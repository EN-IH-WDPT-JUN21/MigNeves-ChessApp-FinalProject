import { Injectable } from '@angular/core';
import { PieceIconInput } from 'ngx-chess-board';

@Injectable({
  providedIn: 'root'
})
export class BoardSettingsService {

  private tileOptions: string[][] = [
    [
      "#9eecdc",
      "#48b79e",
      "Turquoise"
    ],
    [
      "#bdd6eb",
      "#5590b2",
      "Ocean"
    ],
    [
      "#f0d9b5",
      "#b58863",
      "Wood"
    ],
    [
      "#ebecd0",
      "#779556",
      "Green"
    ],
    [
      "#edb6af",
      "#c64839",
      "Red"
    ],
    [
      "#c5c5c5",
      "#888888",
      "Grey"
    ]
   
  ];

  private pieceOptions: string[] = [
    "alpha",
    "california",
    "cardinal",
    "cburnett",
    "chess7",
    "chessnut",
    "companion",
    "dubrovny",
    "fantasy",
    "fresca",
    "gioco",
    "governor",
    "horsey",
    "icpieces",
    "kosal",
    "leipzig",
    "letter",
    "libra",
    "maestro",
    "merida",
    "pirouetti",
    "pixel",
    "reillycraig",
    "riohacha",
    "shapes",
    "spatial",
    "staunty",
    "tatiana"
  ]

  constructor() { }

  getTileColors() {
    let colors = localStorage.getItem("tileOptionId");

    if (colors == null) {
      localStorage.setItem("tileOptionId", "0");
      return this.tileOptions[0];
    } else {
      return this.tileOptions[parseInt(colors)];
    }
  }

  setTileColors(index: number) {
    localStorage.setItem("tileOptionId", index.toString());
  }

  getPieces():PieceIconInput {
    let pieceType = localStorage.getItem("pieces");

    if (pieceType == null) {
      localStorage.setItem("pieces", "california");
      pieceType = "california";
    }

    return {
      whiteKingUrl: "../assets/piece/" + pieceType + "/wK.svg",
    whiteQueenUrl: "../assets/piece/" + pieceType + "/wQ.svg",
    whiteKnightUrl: "../assets/piece/" + pieceType + "/wN.svg",
    whiteRookUrl: "../assets/piece/" + pieceType + "/wR.svg", 
    whitePawnUrl: "../assets/piece/" + pieceType + "/wP.svg",
    whiteBishopUrl: "../assets/piece/" + pieceType + "/wB.svg",
    blackKingUrl: "../assets/piece/" + pieceType + "/bK.svg",
    blackQueenUrl: "../assets/piece/" + pieceType + "/bQ.svg",
    blackKnightUrl: "../assets/piece/" + pieceType + "/bN.svg",
    blackRookUrl: "../assets/piece/" + pieceType + "/bR.svg",
    blackPawnUrl: "../assets/piece/" + pieceType + "/bP.svg",
    blackBishopUrl: "../assets/piece/" + pieceType + "/bB.svg"
    }

  }

  setPieceType(name: string) {
    localStorage.setItem("pieces", name);
  }

  getPieceSetNames() {
    return this.pieceOptions;
  }

  getTileThemes() {
    return this.tileOptions;
  }
}
