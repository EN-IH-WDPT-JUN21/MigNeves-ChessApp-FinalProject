import { FinishedGames } from './../../models/finished-games.mode';
import { BoardSettingsService } from './../../services/board-settings.service';
import { NgxChessBoardComponent, NgxChessBoardView, PieceIconInput } from 'ngx-chess-board';
import { SimplifiedGame } from './../../models/simplified-game-models';
import { GameDatabaseService } from './../../services/game-database.service';
import { AfterViewChecked, AfterViewInit, ChangeDetectorRef, Component, OnInit, QueryList, ViewChildren } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-chess-list',
  templateUrl: './chess-list.component.html',
  styleUrls: ['./chess-list.component.css']
})
export class ChessListComponent implements OnInit, AfterViewChecked{

  listType!: string;
  games!: SimplifiedGame[];
  tileColors!: string[];
  pieces!: PieceIconInput;
  pages!: number;
  page!: number;

  constructor(
    private router: Router,
    private databaseService: GameDatabaseService,
    private settingsService: BoardSettingsService,
    private activatedRoute: ActivatedRoute,
    private changeDetector: ChangeDetectorRef
    ) { 
      this.router.routeReuseStrategy.shouldReuseRoute = () => false;
      // Get colors and pieces from settings
      this.tileColors = this.settingsService.getTileColors();
      this.pieces = this.settingsService.getPieces();

      // if url contains "finished fetch finished games"
      const route = this.router.url;
      route.split("/").forEach(routePart => {
        if (routePart === "finished") {
          this.listType = "finished";

          this.page = this.activatedRoute.snapshot.params['page']; 
          this.databaseService.getAllFinishedGames(this.page).subscribe(
            rawData => {
              let finishedGames: FinishedGames = rawData;
              this.games = finishedGames.games;
              this.pages = finishedGames.pages;
              console.log(this.pages);
            }
          );

        // if url contains open fetch currently open games
        } else if (routePart == "open") {
          this.listType = "open";
          let values = "",
          keys = Object.keys(localStorage),
          i = keys.length;
  
          keys.forEach(key => {
            if (key.includes("game-own-psw-")) {
              if (values !== "") {
                values = values + ",";
              }
              values = values + localStorage.getItem(key) + "-" + key.replace('game-own-psw-','');
            }
          });
  
          this.databaseService.getGamesFromKeys(values).subscribe(
            rawData => {
              this.games = rawData;
            }
          );
        }
      });
    }

    @ViewChildren('board')
    boards!: QueryList<NgxChessBoardView>

  ngOnInit(): void {
  }

  // Set each board to the last position
  ngAfterViewChecked() {
    let length = this.boards.length;
    for (let i = 0; i < length; i++) {
      let board = this.boards.get(i);
      board?.setFEN(this.games[i].fen);
    }
    this.changeDetector.detectChanges();
  }

  // send query to the backend to delete game
  deleteGame(id: number) {
    this.databaseService.deleteGame(id).subscribe(
      rawData => {
        localStorage.removeItem('game-own-psw-' + id);
        localStorage.removeItem('game-opponent-psw-' + id);
        window.location.reload();
      }
    )
  }

  // reroute to view or play the game
  goToGame(id: number) {
    if (this.listType === "open") {
      this.router.navigate(['/game/', id]);
    } else {
      this.router.navigate(['/view', id]);
    } 
  }

  previousPage() {
    this.page--;
    this.router.navigate(['/finished', this.page]);
  }

  nextPage() {
    this.page++;
    this.router.navigate(['/finished', this.page]);
  }
}

import { Pipe, PipeTransform } from '@angular/core';

// Pipe to replace an underscore for a space
@Pipe({name: 'replaceUnderscore'})
export class ReplaceUnderscorePipe implements PipeTransform {
  transform(value: string): string {
    return value? value.replace(/_/g, " ") : value;
  }
}