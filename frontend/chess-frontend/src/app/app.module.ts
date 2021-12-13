import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ChessGameComponent } from './components/chess-game/chess-game.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxChessBoardModule } from 'ngx-chess-board';
import { HomeComponent } from './components/home/home.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { HttpClientModule } from '@angular/common/http';
import { CreateGameModal } from './modal/create-game/create-game.modal';
import { JoinGameModal } from './modal/join-game/join-game.modal';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal, NgbAlertModule, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HeaderComponent } from './components/header/header.component';
import { SettingsComponent } from './components/settings/settings.component';
import { ChessListComponent, ReplaceUnderscorePipe } from './components/chess-list/chess-list.component';
import { GameResultModal } from './modal/game-result/game-result.modal';
import { GameViewComponent } from './components/game-view/game-view.component';
// import { AuthRoutingModule } from './auth-routing.module';

@NgModule({
  declarations: [
    AppComponent,
    ChessGameComponent,
    HomeComponent,
    PageNotFoundComponent,
    CreateGameModal,
    JoinGameModal,
    HeaderComponent,
    SettingsComponent,
    ChessListComponent,
    GameResultModal,
    ReplaceUnderscorePipe,
    GameViewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    NgxChessBoardModule.forRoot(),
    HttpClientModule,
    FormsModule,
    NgbModule,
    NgbAlertModule
    //AuthRoutingModule
  ],
  providers: [NgbActiveModal],
  bootstrap: [AppComponent]
})
export class AppModule { }
