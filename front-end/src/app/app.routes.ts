import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EntrainementMainComponent } from './entrainement-main/entrainement-main.component';
import { ExamenComponent } from './examen/examen.component';
import { EntrainementComponent } from './entrainement/entrainement.component';
import { LoginComponent } from './login/login.component';
import { AssistanceComponent } from "./assistance/assistance.component";

// Importes for for courses
import { CoursSectionComponent } from './cours-section/cours-section.component';
import { CoursComponent } from './cours/cours.component';


export const routes: Routes = [
  // Redirect to /cours when the app is loaded
  { path: '', redirectTo: '/cours', pathMatch: 'full' },
  
  { path: 'cours',  component: CoursComponent },
  { path: 'entrainement', component: EntrainementMainComponent},
  { path: 'examen', component: ExamenComponent},
  { path: 'login', component: LoginComponent},
  { path: 'assistance', component: AssistanceComponent},

  // Path for cours-section component passing the id of the section
  { path: 'cours/:section', component: CoursSectionComponent},

  // Path for entrainement component passing the id of the chapter
  { path: 'entrainement/:chapterId', component: EntrainementComponent},

  // Add other paths here dont forget to import above
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
