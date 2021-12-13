import { FinishedGames } from './../../models/finished-games.mode';
import { BoardSettingsService } from './../../services/board-settings.service';
import { NgxChessBoardComponent, NgxChessBoardView, PieceIconInput } from 'ngx-chess-board';
import { SimplifiedGame } from './../../models/simplified-game-models';
import { GameDatabaseService } from './../../services/game-database.service';
import { AfterViewChecked, AfterViewInit, Component, OnInit, QueryList, ViewChildren } from '@angular/core';
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
    private activatedRoute: ActivatedRoute
    ) { 
      this.tileColors = this.settingsService.getTileColors();
      this.pieces = this.settingsService.getPieces();

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
            }
          );
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

  ngAfterViewChecked() {
    let length = this.boards.length;
    for (let i = 0; i < length; i++) {
      let board = this.boards.get(i);
      board?.setFEN(this.games[i].fen);
    }
  }

  deleteGame(id: number) {
    this.databaseService.deleteGame(id).subscribe(
      rawData => {
        localStorage.removeItem('game-own-psw-' + id);
        localStorage.removeItem('game-opponent-psw-' + id);
        window.location.reload();
      }
    )
  }

  goToGame(id: number) {
    this.router.navigate(['/game/', id]);
  }
}

import { Pipe, PipeTransform } from '@angular/core';
import { Observable } from 'rxjs';

@Pipe({name: 'replaceUnderscore'})
export class ReplaceUnderscorePipe implements PipeTransform {
  transform(value: string): string {
    return value? value.replace(/_/g, " ") : value;
  }
}