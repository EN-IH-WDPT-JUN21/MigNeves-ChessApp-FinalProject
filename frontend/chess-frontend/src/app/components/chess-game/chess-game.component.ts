import { EndResult } from './../../enums/end-result.enums';
import { Game } from '../../models/game.models';
import { GameDatabaseService } from '../../services/game-database.service';
import { Component, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxChessBoardService, PieceIconInput, NgxChessBoardComponent, NgxChessBoardView, HistoryMove } from 'ngx-chess-board';
import { iMove, Move } from 'src/app/models/move.models';
import { Piece } from 'src/app/enums/piece.enums';

@Component({
  selector: 'app-chess-game',
  templateUrl: './chess-game.component.html',
  styleUrls: ['./chess-game.component.css']
})
export class ChessGameComponent implements OnInit {

  // Change remaining colors in css through html .board -> .board-row -> .board-col

  @ViewChild('board', {static: false}) 
  board!: NgxChessBoardView;
  
  lightTileColor = "#f0d9b5";
  darkTileColor = "#b58863";

  pieceIcons: PieceIconInput = {
    whiteKingUrl: "../assets/piece/cburnett/wK.svg",
    whiteQueenUrl: "../assets/piece/cburnett/wQ.svg",
    whiteKnightUrl: "../assets/piece/cburnett/wN.svg",
    whiteRookUrl: "../assets/piece/cburnett/wR.svg", 
    whitePawnUrl: "../assets/piece/cburnett/wP.svg",
    whiteBishopUrl: "../assets/piece/cburnett/wB.svg",
    blackKingUrl: "../assets/piece/cburnett/bK.svg",
    blackQueenUrl: "../assets/piece/cburnett/bQ.svg",
    blackKnightUrl: "../assets/piece/cburnett/bN.svg",
    blackRookUrl: "../assets/piece/cburnett/bR.svg",
    blackPawnUrl: "../assets/piece/cburnett/bP.svg",
    blackBishopUrl: "../assets/piece/cburnett/bB.svg"
  }

  highlightedLightColor = "#829769";
  highlightedDarkColor = "#646f40";

  game!: Game;
  password!: string;
  timeout!: any;
  isMyTurn: boolean = false;
  gameId!: number
  opponentPassword!: string | null;

  constructor(
    private chessBoardService: NgxChessBoardService,
    private activatedRoute: ActivatedRoute,
    private gameDatabaseService: GameDatabaseService) { }

  ngOnInit() {
    this.gameId = this.activatedRoute.snapshot.params['gameId'];
    this.opponentPassword = localStorage.getItem("game-opponent-psw-" + this.gameId);
    this.updateGame();
  }

  updateGame() {
    this.gameDatabaseService.getGameFromId(this.gameId, localStorage.getItem('game-own-psw-'+ this.gameId)).subscribe(rawData => {
      this.game = rawData
      console.log(this.game.fen);
      if (this.game != null) {
        this.intervalManager();
        this.board.setFEN(this.game.fen);
        if (this.game.colorWhite === false) {
          this.board.reverse();
        }
      }
    });
  }

  intervalManager() {
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
        this.timeout = setInterval(() => { this.updateGame(); }, 2000);
      }
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
