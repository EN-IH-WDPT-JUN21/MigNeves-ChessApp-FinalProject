import { SettingsComponent } from './components/settings/settings.component';
import { ChessGameComponent } from './components/chess-game/chess-game.component';
import { HomeComponent } from './components/home/home.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';

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
    path: 'settings',
    component: SettingsComponent
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
