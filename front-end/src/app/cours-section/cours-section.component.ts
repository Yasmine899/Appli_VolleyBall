import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-cours-section',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cours-section.component.html',
  styleUrl: './cours-section.component.scss'
})
export class CoursSectionComponent {
  // The section title and id
  sectionTitle: string;
  id: number;

  // Titles for the sections of the course, should get this from database
  sectionTitles: string[] = [
    "Caractéristiques du jeu",
    "PARTIE 1 - PHILOSOPHIE DES RÈGLES ET ARBITRAGE",
    "PARTIE 2 - SECTION 1: LE JEU",
    "PARTIE 2 - SECTION 2 - LES ARBITRES, LEURS RESPONSABILITÉS ET LES GESTES OFFICIELS",
    "PARTIE 2 - SECTION 3: FIGURES",
    "PARTIE 3: DÉFINITIONS"
  ];

  // Matrix of the subsection titles where one line represents a sections subsection titles, should get this from the database
  @Input() titles: string[][] = [
    ["Chapitre 1", "Chapitre 2", "Chapitre 3", "Chapitre 4"],
    ["Chapitre 5", "Chapitre 6", "Chapitre 7", "Chapitre 8"]
  ];

  // Matrix of the subsection contents where one line represents a sections subsection contents, should get this from the database
  @Input() contents: string[][] = [
    ["Contents of chapitre 1 successfully dynamically loaded", "Contents of chapitre 2 successfully dynamically loaded", "Contents of chapitre 3 successfully dynamically loaded", "Contents of chapitre 4 successfully dynamically loaded"],
    ["Contents of chapitre 5 successfully dynamically loaded", "Contents of chapitre 6 successfully dynamically loaded", "Contents of chapitre 7 successfully dynamically loaded", "Contents of chapitre 8 successfully dynamically loaded"]
  ];

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    if (this.route.snapshot.paramMap.get('section') === null) {
      this.sectionTitle = "ERROR404"
      this.id = -1;
    }
    else {
      this.sectionTitle = this.route.snapshot.paramMap.get('section') as string;
      this.id = this.sectionTitles.indexOf(this.sectionTitle);
    }
  }

}
