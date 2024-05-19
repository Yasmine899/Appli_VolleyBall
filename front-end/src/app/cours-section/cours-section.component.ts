import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ChapitreComponent } from '../chapitre/chapitre.component';

type Chapter = {
  title: string, // The title of the chapter
  intro: string[], // The introduction paragraphs of the chapter
  subTitles: string[], // The titles of the sections of the chapter
  contents: string[][] // The content of the chapter where one line represents a sections paragraphs
};

@Component({
  selector: 'app-cours-section',
  standalone: true,
  imports: [CommonModule, ChapitreComponent],
  templateUrl: './cours-section.component.html',
  styleUrl: './cours-section.component.scss'
})

export class CoursSectionComponent {
  // The section title and id
  sectionTitle: string;
  id: number;

  // Section titles to chapters mapping for the sections of the course, should get this from database
  //             [Title, From chapter, To chapter]
  // Example:    ["Caractéristiques du jeu", 0, 1] here the section "Caractéristiques du jeu" is contains chapter 0 until chapter 1 (chapter 1 not included)
  sectionChapters: [string, number, number][] = [
    // [Title, Chapters]
    ["Caractéristiques du jeu", 0, 1],
    ["PARTIE 1 - PHILOSOPHIE DES RÈGLES ET ARBITRAGE", 1, 2],
    ["PARTIE 2 - SECTION 1: LE JEU", 2, 8],
    ["PARTIE 2 - SECTION 2 - LES ARBITRES, LEURS RESPONSABILITÉS ET LES GESTES OFFICIELS", 8, 9],
    ["PARTIE 2 - SECTION 3: FIGURES", 9, 10],
    ["PARTIE 3: DÉFINITIONS", 10, 11]
  ];

  // List of the chapters, should get this from the database
  @Input() chapters: Chapter[] = [
    {
      title: "Caractéristiques du jeu",
      intro: [
        "Le Volleyball est un sport joué par deux équipes sur un terrain divisé par un filet. Il y a différentes versions disponibles pour offrir de la souplesse de jeu à tous.",
        "Le but du jeu est d’envoyer le ballon par-dessus le filet afin qu’il retombe dans le terrain opposé et d’empêcher cette même action de la part des adversaires. L’équipe a droit à trois touches avant de renvoyer le ballon (en plus du contact effectué par le contre).",
      ],
      subTitles: [],
      contents: [],
    },
    {
      title: "PARTIE 1 - PHILOSOPHIE DES RÈGLES ET ARBITRAGE",
      intro: [
        "Les règles du jeu de volleyball sont établies par la Fédération Internationale de Volleyball (FIVB) et sont utilisées pour tous les niveaux de jeu, des compétitions internationales aux tournois locaux.",
        "Les règles du jeu sont conçues pour garantir que le jeu est joué de manière juste et équitable pour tous les participants."
      ],
      subTitles: ["Le texte des règles"],
      contents: [
        ["Ce texte s’adresse à un large public de Volleyball – joueurs, entraîneurs, arbitres, spectateurs, commentateurs et autres – car la compréhension des règles permet de mieux jouer et d’obtenir une plus grande satisfaction personnelle; les entraîneurs peuvent créer une meilleure structure d’équipe et de meilleures tactiques, en donnant aux joueurs toute latitude pour déployer leurs talents, et la compréhension de la relation entre les règles écrites et les actions réelles sur le terrain permet aux officiels de prendre de meilleures décisions."],
      ],
    },
    {
      title: "Chapitre 1",
      intro: [],
      subTitles: ["Section 1", "Section 2", "Section 3"],
      contents: [
        ["First paragraph of section 1", "Second paragraph of section 1", "Third paragraph of section 1"],
        ["First paragraph of section 2", "Second paragraph of section 2", "Third paragraph of section 2"],
        ["First paragraph of section 3", "Second paragraph of section 3", "Third paragraph of section 3"]
      ],
    },
    {
      title: "Chapitre 2",
      intro: [],
      subTitles: ["Section 1", "Section 2", "Section 3"],
      contents: [
        ["First paragraph of section 1", "Second paragraph of section 1", "Third paragraph of section 1"],
        ["First paragraph of section 2", "Second paragraph of section 2", "Third paragraph of section 2"],
        ["First paragraph of section 3", "Second paragraph of section 3", "Third paragraph of section 3"]
      ],
    }
  ];

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    if (this.route.snapshot.paramMap.get('section') === null) {
      this.sectionTitle = "ERROR404"
      this.id = -1;
    }
    else {
      this.sectionTitle = this.route.snapshot.paramMap.get('section') as string;
      for (let i = 0; i < this.sectionChapters.length; i++) {
        if (this.sectionChapters[i][0] === this.sectionTitle) {
          this.id = i;
          break;
        }
      }
    }
  }

}
