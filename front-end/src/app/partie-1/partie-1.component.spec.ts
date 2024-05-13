import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Partie1Component } from './partie-1.component';

describe('Partie1Component', () => {
  let component: Partie1Component;
  let fixture: ComponentFixture<Partie1Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Partie1Component]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(Partie1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
