import { Component } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { HeaderComponent } from "./header/header.component";
import { ExamenComponent } from "./examen/examen.component";
import { CoursComponent } from "./cours/cours.component";
import { AppRoutingModule } from './app.routes';
import { HttpClientModule } from '@angular/common/http';

@Component({
    selector: 'app-root',
    standalone: true,
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss',
    imports: [RouterOutlet, RouterModule, HeaderComponent, ExamenComponent, CoursComponent, HttpClientModule]
})
export class AppComponent {
  title = 'front-end';
}
