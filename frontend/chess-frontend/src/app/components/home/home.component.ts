import { iGameCreated } from '../../models/game-created.models';
import { GameDatabaseService } from '../../game-database.service';
import { Component, OnInit } from '@angular/core';
import { GameCreated } from '../../models/game-created.models';
import { GameType } from '../../enums/game-type.enums';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(
    private gameService: GameDatabaseService,
    private router: Router) { }

  ngOnInit(): void {
  }

  async createGame(color: string) {
    let createGame: iGameCreated = {
      gameType: GameType.UNKNOWN_UNKNOWN
    }

    let gameCreated: GameCreated = await this.gameService.addGame(new GameCreated(createGame));

    console.log(gameCreated);
    console.log(color);

    if (color === "Random") {
      if (Math.random() < 0.5) {
        color = "White";
      } else {
        color = "Black";
      }
    }

    localStorage.setItem('game-own-psw-' + gameCreated.id, color === "White" ? gameCreated.whitePassword : gameCreated.blackPassword);
    localStorage.setItem('game-opponent-psw-' + gameCreated.id, color === "White" ? gameCreated.blackPassword : gameCreated.whitePassword);
    this.router.navigate(['/game/', gameCreated.id]);
  }

  joinGame(passwordAndId: string) {
    let splitedString = passwordAndId.trim().split("-");
    let id = parseInt(splitedString[1], 10);
    let password = splitedString[0];
    if (splitedString.length == 2) {
      localStorage.setItem("game-own-psw-" + id, password);
    }
    this.router.navigate(['/game/', id]);
  }

}

