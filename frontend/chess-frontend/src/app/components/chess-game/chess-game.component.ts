import { GameResultModal } from './../../modal/game-result/game-result.modal';
import { BoardSettingsService } from './../../services/board-settings.service';
import { EndResult } from './../../enums/end-result.enums';
import { Game } from '../../models/game.models';
import { GameDatabaseService } from '../../services/game-database.service';
import { Component, HostListener, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxChessBoardService, PieceIconInput, NgxChessBoardView, HistoryMove } from 'ngx-chess-board';
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

  constructor(
    private chessBoardService: NgxChessBoardService,
    private activatedRoute: ActivatedRoute,
    private gameDatabaseService: GameDatabaseService,
    private settingsService: BoardSettingsService,
    private clipboard: Clipboard,
    private modalService: NgbModal,
    private router: Router) { 
      this.tileColors = settingsService.getTileColors();
      this.pieces = settingsService.getPieces();
    }

  ngOnInit() {
    this.gameId = this.activatedRoute.snapshot.params['gameId'];
    this.opponentPassword = localStorage.getItem("game-opponent-psw-" + this.gameId);
    this.updateGame();
    this.innerHeight = window.innerHeight;
    this.innerWidth = window.innerWidth;
  }

  ngOnDestroy() {
    clearInterval(this.timeout);
  }

  ngAfterViewInit() {
    this.updateGame();
  }

  gameEnded() {
    if (this.gameOver === false) {
      this.gameOver = true;
      const modalRef = this.modalService.open(GameResultModal);
      modalRef.componentInstance.result = this.game.result;
      modalRef.componentInstance.id = this.gameId;
      modalRef.componentInstance.colorWhite = this.game.colorWhite; 
      localStorage.removeItem('game-own-psw-' + this.gameId);
      localStorage.removeItem('game-opponent-psw-' + this.gameId);
    }
  }

  @HostListener('window:resize', ['$event'])
  onResize() {
    this.innerHeight = window.innerHeight;
    this.innerWidth = window.innerWidth;
  }

  copyPasswordToClipboard() {
    if (this.opponentPassword != null) {
      this.clipboard.copy(this.opponentPassword + "-" + this.gameId);
    }
  }

  updateGame() {
    this.gameDatabaseService.getGameFromId(this.gameId, localStorage.getItem('game-own-psw-'+ this.gameId)).subscribe(rawData => {
      this.game = rawData
      console.log(this.game.fen);
      if (this.game != null) {
        this.intervalManager();
        if (this.board != null) {
          this.board.setFEN(this.game.fen);
          if (this.game.colorWhite === false) {
            this.board.reverse();
          }
        }
        if (this.game.result !== "UNFINISHED") {
          this.gameEnded();
        }
      }
    },
    err => {
      this.router.navigate(['/unauthorized']);
    });
  }

  intervalManager() {
    if (this.game.result === "UNFINISHED") {
      if ((this.game.colorWhite === true && this.game.moves.length % 2 === 0) || (this.game.colorWhite === false && this.game.moves.length % 2 !== 0)) {
        this.isMyTurn = true;
        if (this.timeout != null) {
          clearInterval(this.timeout);
          this.timeout = null;
        }
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

  moveDone() {
    let moves = this.board.getMoveHistory();
    let move = this.getMoveFromHistoryMove(moves[moves.length - 1]);
    this.game.moveToAdd = move;
    this.game.password = localStorage.getItem('game-own-psw-' + this.gameId);
    this.game.fen = this.board.getFEN();
    this.gameDatabaseService.updateGame(this.game).subscribe(
      result => {
        this.updateGame();
      }
    );
  }

  getMoveFromHistoryMove(historyMove: HistoryMove): Move {
    let endResult: EndResult;
    console.log(historyMove)
    if (historyMove.mate === true) {
      endResult = historyMove.color === "white" ? EndResult.WHITE_WON : EndResult.BLACK_WON;
    } else if (historyMove.stalemate) {
      endResult = EndResult.DRAW;
    } else {
      endResult = EndResult.UNFINISHED;
    }

    let move: iMove = {
      gameId: this.game.id,
      move: historyMove.move,
      capture: historyMove.x,
      piece: (historyMove.color.toUpperCase() + "_" + historyMove.piece.toUpperCase()) as Piece,
      inCheck: historyMove.check,
      result: endResult
    }

    console.log(this.game);

    return new Move(move);
  }
}
