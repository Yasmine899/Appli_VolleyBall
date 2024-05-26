import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {CoursApiConnectService} from '../cours-api-connect.service';

@Component({
  selector: 'app-chapitre',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './chapitre.component.html',
  styleUrl: './chapitre.component.scss',
  providers: [CoursApiConnectService]
})
export class ChapitreComponent implements OnInit {
  readonly FIGURE_CHAPITRE_ID: number = 9; // Chapitre 9 is the figure chapter

  @Input() chapitreId: number;
  chapitreData: any;
  showChapitreTitle: boolean;
  isFigureChapitre: boolean; // A figure chapitre is a chapter the contains all the figures


 constructor(private coursApiConnectService: CoursApiConnectService) { }

  ngOnInit(): void {
    console.log("Getting chapitre" + this.chapitreId + " data from API...");
    this.coursApiConnectService.getCoursChapter(this.chapitreId).subscribe((data) => {
      this.chapitreData = data;
    });

    this.showChapitreTitle = this.chapitreId > 0 && this.chapitreId < 99 ? true : false;
    this.isFigureChapitre = this.chapitreId == this.FIGURE_CHAPITRE_ID ? true : false;
  }
}
