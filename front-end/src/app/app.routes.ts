import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CoursComponent } from './cours/cours.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { EntrainementComponent } from './entrainement/entrainement.component';
import { ExamenComponent } from './examen/examen.component';

export const routes: Routes = [
  // Other routes...
  { path: 'cours', component: CoursComponent },
  { path: 'entrainement', component: EntrainementComponent},
  { path: 'examen', component: ExamenComponent},
  { path: '', component: LandingPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
