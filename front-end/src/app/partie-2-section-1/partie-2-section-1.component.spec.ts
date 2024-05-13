import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Partie2Section1Component } from './partie-2-section-1.component';

describe('Partie2Section1Component', () => {
  let component: Partie2Section1Component;
  let fixture: ComponentFixture<Partie2Section1Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Partie2Section1Component]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(Partie2Section1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
