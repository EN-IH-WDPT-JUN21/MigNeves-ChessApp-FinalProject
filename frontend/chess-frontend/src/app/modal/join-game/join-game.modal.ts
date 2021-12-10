import {Component, EventEmitter, Input, Output} from '@angular/core';

import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-join-game',
  templateUrl: './join-game.modal.html',
  styleUrls: ['../../../assets/modal.css']
})
export class JoinGameModal {
  closeResult = '';
  passwordAndId = '';

  @Output() gameJoined: EventEmitter<string> = new EventEmitter();

  constructor(private modalService: NgbModal) {}

  open(content: any) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  joinGame(passwordAndId: any){
      this.gameJoined.emit(passwordAndId);
  }
}