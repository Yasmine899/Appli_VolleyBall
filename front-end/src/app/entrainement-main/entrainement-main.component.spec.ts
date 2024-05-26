import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EntrainementMainComponent } from './entrainement-main.component';

describe('EntrainementMainComponent', () => {
  let component: EntrainementMainComponent;
  let fixture: ComponentFixture<EntrainementMainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EntrainementMainComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EntrainementMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
