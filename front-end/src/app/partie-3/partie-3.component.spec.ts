import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Partie3Component } from './partie-3.component';

describe('Partie3Component', () => {
  let component: Partie3Component;
  let fixture: ComponentFixture<Partie3Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Partie3Component]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(Partie3Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
