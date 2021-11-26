import { Component, OnInit } from '@angular/core';
import { NgxChessBoardService, PieceIconInput } from 'ngx-chess-board';

@Component({
  selector: 'app-chess-game',
  templateUrl: './chess-game.component.html',
  styleUrls: ['./chess-game.component.css']
})
export class ChessGameComponent implements OnInit {

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

  lightTileMovedColor = "#cdd26a";
  darkTileMovedColor = "#aaa23a";

  constructor(private chessBoardService: NgxChessBoardService) { }

  ngOnInit(): void {
  }

}
