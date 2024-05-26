import { Component } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { HeaderComponent } from "./header/header.component";
import { ExamenComponent } from "./examen/examen.component";
import { CoursComponent } from "./cours/cours.component";
import { HttpClientModule } from '@angular/common/http';
import { EntrainementComponent } from './entrainement/entrainement.component';
import { EntrainementMainComponent } from './entrainement-main/entrainement-main.component';

@Component({
    selector: 'app-root',
    standalone: true,
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss',
    imports: [RouterOutlet, RouterModule, HeaderComponent, ExamenComponent, CoursComponent, HttpClientModule, EntrainementMainComponent,
      EntrainementComponent]
})
export class AppComponent {
  title = 'front-end';
}
