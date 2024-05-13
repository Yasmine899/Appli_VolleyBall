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
  id: number;
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
    if (this.route.snapshot.paramMap.get('id') === null) {
      this.id = -1
    }
    else {
      this.id = parseInt(this.route.snapshot.paramMap.get('id') as string);
    }
  }

}
