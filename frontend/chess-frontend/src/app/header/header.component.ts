import { Component, ElementRef, HostListener, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  @ViewChild('stickyMenu')
  menuElement!: ElementRef;
  sticky: boolean = false;
  elementPosition: any;

  constructor() {}

  ngOnInit(): void {}

  ngAfterViewInit(){
    this.elementPosition = this.menuElement.nativeElement.offsetTop;
  }

  @HostListener('window:scroll', ['$event'])
  handleScroll(){
    //const windowScroll = window.pageYOffset;
    if(window.scrollY >= 1){
      this.sticky = true;
    } else {
      this.sticky = false;
    }
  }
}
