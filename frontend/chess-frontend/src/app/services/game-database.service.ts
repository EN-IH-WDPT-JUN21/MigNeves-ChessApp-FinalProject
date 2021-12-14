import { Move } from './../models/move.models';
import { GameCreated } from '../models/game-created.models';
import { SimplifiedGame } from '../models/simplified-game-models';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Game } from '../models/game.models';
import { FinishedGames } from '../models/finished-games.mode';

@Injectable({
  providedIn: 'root'
})
export class GameDatabaseService {

  private readonly baseUrl = 'http://localhost:8000/chess'

  constructor(private http: HttpClient) { }

  getGamesFromUser(userId: string): Observable<SimplifiedGame[]> {
    return this.http.get<SimplifiedGame[]>(`${this.baseUrl}/game/${userId}`);
  }

  getGameFromId(gameId: number, password: string | null): Observable<Game> {
    return this.http.get<Game>(`${this.baseUrl}/game?gameId=${gameId}&password=${password}`);
  }

  async addGame(game: GameCreated): Promise<GameCreated> {
    return await this.http.post<GameCreated>(`${this.baseUrl}/game`, game).toPromise();
  }
  
  updateGame(game: Game): Observable<Game> {
    return this.http.put<Game>(`${this.baseUrl}/game`, game);
  }

  deleteGame(gameId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/game/${gameId}`);
  }

  getGamesFromKeys(keys: string): Observable<SimplifiedGame[]> {
    return this.http.get<SimplifiedGame[]>(`${this.baseUrl}/game?keys=${keys}`);
  }

  getAllFinishedGames(page: number): Observable<FinishedGames> {
    return this.http.get<FinishedGames>(`${this.baseUrl}/game?page=${page}`);
  }

  getMovesFromGame(gameId: number): Observable<Move[]> {
    return this.http.get<Move[]>(`${this.baseUrl}/move/${gameId}`);
  }
}
