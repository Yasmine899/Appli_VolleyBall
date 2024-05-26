import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-entrainement-main',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './entrainement-main.component.html',
  styleUrl: './entrainement-main.component.scss'
})
export class EntrainementMainComponent {
chapitres: string[] =[
  "Chapitre 1 - Installations et équipements",
  "Chapitre 2 - Participants",
  "Chapitre 3 - Formule de jeu",
  "Chapitre 4 - Actions de jeu",
  "Chapitre 5 - Interruptions, retards de jeu et intervalles",
  "Chapitre 6 - Le joueur libéro",
  "Chapitre 7 - Conduite des participants",
  "Chapitre 8 - Les arbitres leurs responsabilités et les gestes officiels",
];


}
