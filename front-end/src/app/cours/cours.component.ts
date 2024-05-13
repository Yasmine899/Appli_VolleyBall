import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CaracteristiquesDuJeuComponent } from '../caracteristiques-du-jeu/caracteristiques-du-jeu.component';

@Component({
  selector: 'app-cours',
  standalone: true,
  imports: [RouterModule, CommonModule, CaracteristiquesDuJeuComponent],
  templateUrl: './cours.component.html',
  styleUrl: './cours.component.scss'
})
export class CoursComponent {
  // Titles for the sections of the course
  titles: string[] = [
    "Caractéristiques du jeu",
    "PARTIE 1 - PHILOSOPHIE DES RÈGLES ET ARBITRAGE",
    "PARTIE 2 - SECTION 1: LE JEU",
    "PARTIE 2 - SECTION 2 - LES ARBITRES, LEURS RESPONSABILITÉS ET LES GESTES OFFICIELS",
    "PARTIE 2 - SECTION 3: FIGURES",
    "PARTIE 3: DÉFINITIONS"
  ];
}