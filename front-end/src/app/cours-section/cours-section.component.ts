import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ChapitreComponent } from '../chapitre/chapitre.component';
import {CoursApiConnectService} from '../cours-api-connect.service';

@Component({
  selector: 'app-cours-section',
  standalone: true,
  imports: [CommonModule, ChapitreComponent],
  templateUrl: './cours-section.component.html',
  styleUrl: './cours-section.component.scss',
  providers: [CoursApiConnectService]
})

export class CoursSectionComponent implements OnInit{
  // The section title and id
  sectionTitle: string;
  sectionId: number;


  constructor(private route: ActivatedRoute, private coursApiConnectService: CoursApiConnectService) { }

  ngOnInit(): void {
    // Reads course section title from URL
    if (this.route.snapshot.paramMap.get('section') !== null) {
      this.sectionTitle = this.route.snapshot.paramMap.get('section') as string;
    }
    else {
      console.error("No section title found in URL");
    }
    }

    /*console.log("Getting section" + this.sectionId + " data from API...");
    this.coursApiConnectService.getSectionId(this.sectionTitle).subscribe((data) => {
      this.chapitreData = data;
    });*/

  }
