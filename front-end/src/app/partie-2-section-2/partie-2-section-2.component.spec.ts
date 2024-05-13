import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Partie2Section2Component } from './partie-2-section-2.component';

describe('Partie2Section2Component', () => {
  let component: Partie2Section2Component;
  let fixture: ComponentFixture<Partie2Section2Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Partie2Section2Component]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(Partie2Section2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
