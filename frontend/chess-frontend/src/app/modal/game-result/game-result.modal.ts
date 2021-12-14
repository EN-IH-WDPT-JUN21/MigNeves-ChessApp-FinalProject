import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-game-result',
  templateUrl: './game-result.modal.html'
})
export class GameResultModal {
  @Input() result!: string;
  @Input() id!: number;
  @Input() colorWhite!: boolean;

  constructor(
      public activeModal: NgbActiveModal,
      private router: Router) {}

  rerouteView() {
      this.activeModal.close('Close click');
      localStorage.removeItem('game-own-psw-' + this.id);
      localStorage.removeItem('game-opponent-psw-' + this.id);
      this.router.navigate(['/view/', this.id])
  }

  rerouteHome() {
    this.activeModal.close('Close click');
    localStorage.removeItem('game-own-psw-' + this.id);
    localStorage.removeItem('game-opponent-psw-' + this.id);
    this.router.navigate(['/']);
  }
}