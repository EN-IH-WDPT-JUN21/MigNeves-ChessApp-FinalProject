import { ActivatedRoute } from '@angular/router';
import { GameDatabaseService } from './../../services/game-database.service';
import { BoardSettingsService } from './../../services/board-settings.service';
import { AfterViewChecked, ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { PieceIconInput, NgxChessBoardView } from 'ngx-chess-board';
import { Move } from 'src/app/models/move.models';

@Component({
  selector: 'app-game-view',
  templateUrl: './game-view.component.html',
  styleUrls: ['./game-view.component.css']
})
export class GameViewComponent implements OnInit, AfterViewChecked {

  game!: Move[];
  gameId!: number;
  innerHeight: any;
  innerWidth: any;
  tileColors: string[];
  pieces: PieceIconInput;
  moveIndex!: number;
  
  @ViewChild('board', {static: false})
  board!: NgxChessBoardView;

  constructor(
    private settingsService: BoardSettingsService,
    private databaseService: GameDatabaseService,
    private activatedRoute: ActivatedRoute,
    private changeDetector: ChangeDetectorRef
  ) {
    // Get colors and pieces from settings
    this.tileColors = this.settingsService.getTileColors();
    this.pieces = this.settingsService.getPieces();

    this.innerHeight = window.innerHeight;
    this.innerWidth = window.innerWidth;

    this.gameId = this.activatedRoute.snapshot.params['gameId'];
    this.databaseService.getMovesFromGame(this.gameId).subscribe(
      rawData => {
        this.game = rawData;
      }
    )
  }

  ngOnInit(): void {
  }

  // Keep track of current move number and only load until that point
  ngAfterViewChecked(): void {
    if (this.game != null) {
      let move
      try {
        let move = localStorage.getItem('move');
      } catch {

      }
      if (move != null) {
        this.moveIndex = parseInt(move);
      } else {
        localStorage.setItem('move', (this.game.length - 1).toString());
      }
      if (this.moveIndex == null || this.moveIndex >= this.game.length) {
        this.moveIndex = this.game.length - 1;
      }
      
      this.board.reset();

      for(let i = 0; i <= this.moveIndex; i++) {
        this.board.move(this.game[i].move);
      }
  
      // Detect changes in DOM
      this.changeDetector.detectChanges();
    }
  }

  // Undo last move
  previousMove() {
    if (this.moveIndex >= 0) {
      this.moveIndex--;
      localStorage.setItem('move', this.moveIndex.toString());
      this.board.undo();
    }
  }

  // Do next move
  nextMove() {
    if (this.game.length !== this.moveIndex + 1) {
      this.moveIndex++;
      localStorage.setItem('move', this.moveIndex.toString());
      this.board.move(this.game[this.moveIndex].move);
    }
  }
}
