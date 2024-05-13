import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CaracteristiquesDuJeuComponent } from './caracteristiques-du-jeu.component';

describe('CaracteristiquesDuJeuComponent', () => {
  let component: CaracteristiquesDuJeuComponent;
  let fixture: ComponentFixture<CaracteristiquesDuJeuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CaracteristiquesDuJeuComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CaracteristiquesDuJeuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
