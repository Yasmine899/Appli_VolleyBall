import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CoursComponent } from './cours/cours.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { EntrainementComponent } from './entrainement/entrainement.component';
import { ExamenComponent } from './examen/examen.component';

// Importes for section of the course
import { CaracteristiquesDuJeuComponent } from './caracteristiques-du-jeu/caracteristiques-du-jeu.component';
import { Partie1Component } from './partie-1/partie-1.component';
import { Partie2Section1Component } from './partie-2-section-1/partie-2-section-1.component';
import { Partie2Section2Component } from './partie-2-section-2/partie-2-section-2.component';
import { Partie2Section3Component } from './partie-2-section-3/partie-2-section-3.component';
import { Partie3Component } from './partie-3/partie-3.component';

export const routes: Routes = [
  { path: 'cours', component: CoursComponent },
  { path: 'caracteristiques-du-jeu', component: CaracteristiquesDuJeuComponent},
  { path: 'entrainement', component: EntrainementComponent},
  { path: 'examen', component: ExamenComponent},
  { path: '', component: LandingPageComponent},

  // Paths for sections of the course
  { path: 'partie-1', component: Partie1Component},
  { path: 'partie-2-section-1', component: Partie2Section1Component},
  { path: 'partie-2-section-2', component: Partie2Section2Component},
  { path: 'partie-2-section-3', component: Partie2Section3Component},
  { path: 'partie-3', component: Partie3Component}

  // Add other paths here dont forget to import above
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
