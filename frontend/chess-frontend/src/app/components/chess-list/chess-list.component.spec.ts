import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChessListComponent } from './chess-list.component';

describe('ChessListComponent', () => {
  let component: ChessListComponent;
  let fixture: ComponentFixture<ChessListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChessListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChessListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
