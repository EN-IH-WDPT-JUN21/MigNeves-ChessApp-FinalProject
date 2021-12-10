import { Component, OnInit } from '@angular/core';
import { BoardSettingsService } from 'src/app/services/board-settings.service';

@Component({
  selector: 'app-page-not-found',
  templateUrl: './page-not-found.component.html',
  styleUrls: ['./page-not-found.component.css']
})
export class PageNotFoundComponent implements OnInit {

  colors

  constructor(private service: BoardSettingsService) { 
    this.colors = service.getTileColors();
  }

  ngOnInit(): void {
  }

}
