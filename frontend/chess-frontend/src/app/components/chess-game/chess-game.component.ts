import { GameResultModal } from './../../modal/game-result/game-result.modal';
import { BoardSettingsService } from './../../services/board-settings.service';
import { EndResult } from './../../enums/end-result.enums';
import { Game } from '../../models/game.models';
import { GameDatabaseService } from '../../services/game-database.service';
import { Component, HostListener, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PieceIconInput, NgxChessBoardView, HistoryMove } from 'ngx-chess-board';
import { iMove, Move } from 'src/app/models/move.models';
import { Piece } from 'src/app/enums/piece.enums';
import { Clipboard } from '@angular/cdk/clipboard';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-chess-game',
  templateUrl: './chess-game.component.html',
  styleUrls: ['./chess-game.component.css']
})
export class ChessGameComponent implements OnInit {

  // Change remaining colors in css through html .board -> .board-row -> .board-col

  @ViewChild('board', {static: false}) 
  board!: NgxChessBoardView;

  game!: Game;
  password!: string;
  timeout!: any;
  isMyTurn: boolean = false;
  gameId!: number;
  opponentPassword!: string | null;
  innerHeight: any;
  innerWidth: any;
  tileColors: string[];
  pieces: PieceIconInput;
  gameOver: boolean = false;
  saveMove: boolean = true;

  constructor(
    private activatedRoute: ActivatedRoute,
    private gameDatabaseService: GameDatabaseService,
    private settingsService: BoardSettingsService,
    private clipboard: Clipboard,
    private modalService: NgbModal,
    private router: Router) { 
      // Get colors and pieces from settings
      this.tileColors = this.settingsService.getTileColors();
      this.pieces = this.settingsService.getPieces();
    }

  ngOnInit() {
    // Get id from url, opponent password from localStorage and fetch the game
    this.gameId = this.activatedRoute.snapshot.params['gameId'];
    this.opponentPassword = localStorage.getItem("game-opponent-psw-" + this.gameId);
    this.updateGame();
    this.innerHeight = window.innerHeight;
    this.innerWidth = window.innerWidth;
  }

  ngOnDestroy() {
    // Stop interval if it is running
    clearInterval(this.timeout);
  }

  ngAfterViewInit() {
    // Fetch game
    this.updateGame();
  }

  gameEnded() {
    // If the game has ended and the modal has not yet been displayed, display the result modal
    if (this.gameOver === false) {
      this.gameOver = true;
      const modalRef = this.modalService.open(GameResultModal);
      modalRef.componentInstance.result = this.game.result;
      modalRef.componentInstance.id = this.gameId;
      modalRef.componentInstance.colorWhite = this.game.colorWhite; 
    }
  }

  @HostListener('window:resize', ['$event'])
  onResize() {
    // get current window height and width when resizing
    this.innerHeight = window.innerHeight;
    this.innerWidth = window.innerWidth;
  }

  copyPasswordToClipboard() {
    // Copy opponent password to clipboard
    if (this.opponentPassword != null) {
      this.clipboard.copy(this.opponentPassword + "-" + this.gameId);
    }
  }

  updateGame() {
    // Fetch game from the database
    this.gameDatabaseService.getGameFromId(this.gameId, localStorage.getItem('game-own-psw-'+ this.gameId)).subscribe(rawData => {
      this.game = rawData
      if (this.game != null) {
        // call interval manager
        this.intervalManager();
        if (this.board != null) {
          // if board already exists reset board to the current game position and stop events from being triggered while loading moves
          this.board.reset();
          this.saveMove = false;
          this.game.moves.forEach(move => {
            this.board.move(move.move);
          });
          this.saveMove = true;

          // If black player rotate board
          if (this.game.colorWhite === false) {
            this.board.reverse();
          }
        }
        // If game ended trigger modal
        if (this.game.result !== "UNFINISHED") {
          this.gameEnded();
        }
      }
    },
    err => {
      if (err.message.contains("UNAUTHORIZED")) {
        this.router.navigate(['/unauthorized']);
      }
    });
  }

  intervalManager() {
    // If game did not finish and not your turn start interval to fetch the game position
    if (this.game.result === "UNFINISHED") {
      if ((this.game.colorWhite === true && this.game.moves.length % 2 === 0) || (this.game.colorWhite === false && this.game.moves.length % 2 !== 0)) {
        this.isMyTurn = true;
        if (this.timeout != null) {
          clearInterval(this.timeout);
          this.timeout = null;
        }

      // if your turn stop interval
      } else {
        this.isMyTurn = false;
        if (this.timeout == null) {
          console.log("start interval")
          this.timeout = setInterval(() => { this.updateGame(); }, 1000);
        }
      }
    } else {
      clearInterval(this.timeout);
    }
  }

  // Method triggered when a move is played
  moveDone() {
    // Only trigger if saveMode is true
    if (this.saveMove) {
      // Get last move and post to the backend to store
      let moves = this.board.getMoveHistory();
      let move = this.getMoveFromHistoryMove(moves[moves.length - 1]);
      this.game.moveToAdd = move;
      this.game.password = localStorage.getItem('game-own-psw-' + this.gameId);
      this.gameDatabaseService.updateGame(this.game).subscribe(
        result => {
          this.updateGame();
        }
      );
    }
  }

  // Convert HistoryMove into Move to send to the backend
  getMoveFromHistoryMove(historyMove: HistoryMove): Move {
    let endResult: EndResult;
    console.log(historyMove)
    if (historyMove.mate === true && historyMove.stalemate === true) {
      if (this.getAllLegalMoves() === 0) {
        endResult = EndResult.DRAW;
      } else {
        endResult = EndResult.UNFINISHED;
      }
    } else if (historyMove.mate === true) {
      endResult = historyMove.color === "white" ? EndResult.WHITE_WON : EndResult.BLACK_WON;
    } else {
      endResult = EndResult.UNFINISHED;
    }

    let move: iMove = {
      gameId: this.game.id,
      move: historyMove.move,
      capture: historyMove.x,
      piece: (historyMove.color.toUpperCase() + "_" + historyMove.piece.toUpperCase()) as Piece,
      inCheck: historyMove.check,
      result: endResult,
      fen: this.board.getFEN()
    }

    return new Move(move);
  }

  // Method to check stalemate position
  getAllLegalMoves(): number {
    let legalMoves = 0;
    if(this.board != null) {
      let fen = this.board.getFEN();
      fen = fen.split(" ")[0];
      let rows = fen.split("/");
      console.log(rows)
      if (rows.length === 8) {
        for (let singleRow = 0; singleRow < 8; singleRow++) {
          rows[singleRow] = rows[singleRow].replace("1", " ");
          rows[singleRow] = rows[singleRow].replace("2", "  ");
          rows[singleRow] = rows[singleRow].replace("3", "   ");
          rows[singleRow] = rows[singleRow].replace("4", "    ");
          rows[singleRow] = rows[singleRow].replace("5", "     ");
          rows[singleRow] = rows[singleRow].replace("6", "      ");
          rows[singleRow] = rows[singleRow].replace("7", "       ");
          rows[singleRow] = rows[singleRow].replace("8", "        ");   
        }
        let moves: string[] = []
    
        for (let r = 1; r < 9; r++) {
          for (let c = 1; c < 9; c++) {
            if ((this.game.colorWhite === true && rows[r-1].charCodeAt(c-1) >= 97 && rows[r-1].charCodeAt(c-1) <= 122)
            ||  (this.game.colorWhite === false && rows[r-1].charCodeAt(c-1) >= 66 && rows[r-1].charCodeAt(c-1) <= 90)) {
              for (let r2 = 1; r2 < 9; r2++) {
                for (let c2 = 1; c2 < 9; c2++) {
                  if (c !== c2 || r !== r2){
                    moves.push(this.getMoveFromIndexes(c,9-r,c2,9-r2));
                  }
                }
              }
            }
          }
        }
    
        this.saveMove = false;
        moves.forEach(move => {
          let numMoves = this.board.getMoveHistory().length;
          this.board.move(move);
          if (numMoves !== this.board.getMoveHistory().length) {
            this.board.undo();
            legalMoves++;
          }
        });
        this.saveMove = true;
      }
    }
    return legalMoves;
  }

  // Convert row and column indexes to chess moves
  getMoveFromIndexes(c1: number, r1: number, c2: number, r2: number): string {
    return(String.fromCharCode(c1 + 96) + r1.toString() + String.fromCharCode(c2 + 96) + r2)
  }
}
