import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { EntrainementComponent } from './entrainement/entrainement.component';
import { ExamenComponent } from './examen/examen.component';
import { AssistanceComponent } from "./assistance/assistance.component";

// Importes for for courses
import { CoursSectionComponent } from './cours-section/cours-section.component';
import { CoursComponent } from './cours/cours.component';

export const routes: Routes = [
  { path: 'cours', component: CoursComponent },
  { path: 'entrainement', component: EntrainementComponent},
  { path: 'examen', component: ExamenComponent},
  { path: 'assistance', component: AssistanceComponent},
  { path: '', component: LandingPageComponent},

  // Path for cours-section component passing the id of the section
  { path: 'cours-section/:id', component: CoursSectionComponent},

  // Add other paths here dont forget to import above
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
