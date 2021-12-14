import { ChessListComponent } from './components/chess-list/chess-list.component';
import { SettingsComponent } from './components/settings/settings.component';
import { ChessGameComponent } from './components/chess-game/chess-game.component';
import { HomeComponent } from './components/home/home.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { GameViewComponent } from './components/game-view/game-view.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'game/:gameId',
    component: ChessGameComponent
  },
  {
    path: 'view/:gameId',
    component: GameViewComponent
  },
  {
    path: 'settings',
    component: SettingsComponent
  },
  {
    path: 'open',
    component: ChessListComponent
  },
  {
    path: 'finished/:page',
    component: ChessListComponent
  },
  {
    path: '**',
    component: PageNotFoundComponent,
   }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
