import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Partie2Section3Component } from './partie-2-section-3.component';

describe('Partie2Section3Component', () => {
  let component: Partie2Section3Component;
  let fixture: ComponentFixture<Partie2Section3Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Partie2Section3Component]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(Partie2Section3Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
