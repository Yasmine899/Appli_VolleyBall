import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-cours',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './cours.component.html',
  styleUrl: './cours.component.scss'
})
export class CoursComponent {
  // Titles for the sections of the course, should get this from database
  titles: string[] = [
    "Caractéristiques du jeu",
    "PARTIE 1 - PHILOSOPHIE DES RÈGLES ET ARBITRAGE",
    "PARTIE 2 - SECTION 1: LE JEU",
    "PARTIE 2 - SECTION 2 - LES ARBITRES, LEURS RESPONSABILITÉS ET LES GESTES OFFICIELS",
    "PARTIE 2 - SECTION 3: FIGURES",
    "PARTIE 3: DÉFINITIONS",
    "ADDITIONAL SECTIONS SUCCESFULLY DYNAMICALLY LOADED 1",
    "ADDITIONAL SECTIONS SUCCESFULLY DYNAMICALLY LOADED 2",
    "ADDITIONAL SECTIONS SUCCESFULLY DYNAMICALLY LOADED 3",
  ];
}
