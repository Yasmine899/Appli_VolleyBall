import { Component, Input, input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-chapitre',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './chapitre.component.html',
  styleUrl: './chapitre.component.scss'
})
export class ChapitreComponent {
  @Input() title: string; // The title of the chapter
  hasIntroParagraph: boolean; // Whether the chapter has an introduction paragraph
  @Input() intro: string[]; // The introduction paragraphs of the chapter

  @Input() subSectionTitles: string[]; // The titles of the sections of the chapter
  @Input() content: string[]; // The content of the chapter
}
