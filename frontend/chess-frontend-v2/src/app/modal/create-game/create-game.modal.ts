import {Component, EventEmitter, Output} from '@angular/core';

import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-create-game',
  templateUrl: './create-game.modal.html'
})
export class CreateGameModal {
  closeResult = '';
  color: string = "";
  colorOptions: string[] = [
    "White",
    "Black",
    "Random"
  ]

  @Output() gameCreated: EventEmitter<string> = new EventEmitter();

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

  createGame(color: any){
      this.gameCreated.emit(color);
  }
}