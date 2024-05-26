import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { ChapitreComponent } from '../chapitre/chapitre.component';
import {CoursApiConnectService} from '../cours-api-connect.service';

@Component({
  selector: 'app-cours-section',
  standalone: true,
  imports: [CommonModule, ChapitreComponent, RouterModule],
  templateUrl: './cours-section.component.html',
  styleUrl: './cours-section.component.scss',
  providers: [CoursApiConnectService]
})

export class CoursSectionComponent implements OnInit{
  // The section title and id
  sectionTitle: string;
  sectionId: number;
  showTableofContents: boolean = false;
  containingChapters: number[];


  constructor(private route: ActivatedRoute, private coursApiConnectService: CoursApiConnectService) { }

  ngOnInit(): void {
    // Reads course section title from URL
    if (this.route.snapshot.paramMap.get('section') !== null) {
      this.sectionTitle = this.route.snapshot.paramMap.get('section') as string;
    }
    else {
      console.error("No section title found in URL");
    }

    // Gets the section id from the API
    this.coursApiConnectService.getCoursSections().subscribe((coursSectionsData : any) => {
      for (let i = 0; i < coursSectionsData.length; i++) {
        if (coursSectionsData[i].description == this.sectionTitle) {
          this.sectionId = coursSectionsData[i].id;
          break;
        }
      }

      // Gets the list of chapters for this section
      this.coursApiConnectService.getSectionChapters(this.sectionId).subscribe((data : any) => {
        this.containingChapters = data;

        // If there are chapters in this section between 1 and 98, show the table of contents
        for (let i = 0; i < this.containingChapters.length; i++) {
          if (this.containingChapters[i] > 0 && this.containingChapters[i] < 99) {
            this.showTableofContents = true;
            break;
          }
        }
      });

    });
  }
}
