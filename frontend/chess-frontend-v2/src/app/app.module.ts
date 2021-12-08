import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeComponent } from './components/home/home.component';
import { ChessGameComponent } from './components/chess-game/chess-game.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { CreateGameModal } from './modal/create-game/create-game.modal';
import { JoinGameModal } from './modal/join-game/join-game.modal';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { NgxChessBoardModule } from 'ngx-chess-board';

@NgModule({
  declarations: [
    AppComponent,
    ChessGameComponent,
    HomeComponent,
    PageNotFoundComponent,
    CreateGameModal,
    JoinGameModal
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    NgxChessBoardModule.forRoot(),
    HttpClientModule,
    FormsModule,
    NgModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
