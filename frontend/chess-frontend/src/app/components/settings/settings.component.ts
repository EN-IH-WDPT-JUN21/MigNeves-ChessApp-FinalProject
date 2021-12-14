import { PieceIconInput } from 'ngx-chess-board';
import { Component, OnInit } from '@angular/core';
import { BoardSettingsService } from 'src/app/services/board-settings.service';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  colors!: string[];
  pieces!: PieceIconInput;
  pieceArray!: string[];

  pieceSetNames!: string[];
  colorTileThemes!: string[][]

  constructor(private service: BoardSettingsService) { 
    
    this.updateValues();
  }

  ngOnInit(): void {
  }

  // Get pieces url and color theme
  updateValues() {
    this.pieces = this.service.getPieces();
    this.pieceArray = [
      this.pieces.blackRookUrl,
      this.pieces.blackQueenUrl,
      this.pieces.blackKingUrl,
      this.pieces.blackPawnUrl,
      this.pieces.blackKnightUrl,
      this.pieces.blackBishopUrl,
      this.pieces.whitePawnUrl,
      this.pieces.whiteKnightUrl,
      this.pieces.whiteBishopUrl,
      this.pieces.whiteRookUrl,
      this.pieces.whiteQueenUrl,
      this.pieces.whiteKingUrl
    ]
    this.colors = this.service.getTileColors();
    this.pieceSetNames = this.service.getPieceSetNames();
    this.colorTileThemes = this.service.getTileThemes();
  }

  // Set color theme
  tileChange(id: number) {
      this.service.setTileColors(id);
      this.updateValues()
  }

  // Set pieces url
  pieceSetChange(name: string) {
    this.service.setPieceType(name);
    this.updateValues();
  }

}
