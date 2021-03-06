import { iGameCreated } from '../../models/game-created.models';
import { GameDatabaseService } from '../../services/game-database.service';
import { Component, Input, OnInit, Output, ViewChild, EventEmitter } from '@angular/core';
import { GameCreated } from '../../models/game-created.models';
import { Router } from '@angular/router';
import { BoardSettingsService } from 'src/app/services/board-settings.service';
import { NgxChessBoardView } from 'ngx-chess-board';
import { Game } from 'src/app/models/game.models';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  @ViewChild('board', {static: false}) 
  board!: NgxChessBoardView;

  tileColors;
  pieces;
  showAlert: boolean = false;

  constructor(
    private gameService: GameDatabaseService,
    private router: Router,
    private settingsService: BoardSettingsService
    ) {
      this.tileColors = this.settingsService.getTileColors();
      this.pieces = this.settingsService.getPieces();
    }

  ngOnInit() {
  }

  // Request backend to create a game. The game color can be selected as random
  async createGame(color: string) {
    let activeGames = 0;
    Object.keys(localStorage).forEach(key => {
      if (key.includes("game-own-psw-")) {
        activeGames++;
      }
    });

    // Only 10 games can be active at a time
    if (activeGames === 10) {
      this.showAlert = true;
    } else {
      if (color === "Random") {
        if (Math.random() < 0.5) {
          color = "White";
        } else {
          color = "Black";
        }
      }
  
      let createGame: iGameCreated = {
        whiteOwner: color === "White" ? true : false
      }
  
      let gameCreated: GameCreated = await this.gameService.addGame(new GameCreated(createGame));
  
      // Save keys in localStorage
      localStorage.setItem('game-own-psw-' + gameCreated.id, color === "White" ? gameCreated.whitePassword : gameCreated.blackPassword);
      localStorage.setItem('game-opponent-psw-' + gameCreated.id, color === "White" ? gameCreated.blackPassword : gameCreated.whitePassword);
      this.router.navigate(['/game/', gameCreated.id]);
    }
  }

  // Join a game already created through the usage of the provided key
  joinGame(passwordAndId: string) {
    let activeGames = 0;
    Object.keys(localStorage).forEach(key => {
      if (key.includes("game-own-psw-")) {
        activeGames++;
      }
    });

    // Only 10 games can be active at a time
    if (activeGames === 10) {
      this.showAlert = true;
    } else {
      let splitedString = passwordAndId.trim().split("-");
      let id = parseInt(splitedString[1], 10);
      let password = splitedString[0];
      if (splitedString.length === 2 && id !== NaN) {
        this.gameService.getGameFromId(id, password).subscribe(
          rawData => {
            let game: Game = rawData;
            if (game != null) {
              if (splitedString.length == 2) {
                localStorage.setItem("game-own-psw-" + id, password);
              }
              this.router.navigate(['/game/', id]);
            }
          }
        )
      }
    }
  }

  // Reroute to see currently open games
  openGames() {
    this.router.navigate(['/open']);
  }

  // Reroute to see finished games
  finishedGames() {
    localStorage.removeItem('move');
    this.router.navigate(['/finished', 1]);
  }
}

