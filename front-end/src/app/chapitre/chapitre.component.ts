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
  @Input() chapitreId: number;
  chapitreData: any;


 constructor(private coursApiConnectService: CoursApiConnectService) { }

  ngOnInit(): void {
    console.log("Getting chapitre" + this.chapitreId + " data from API...");
    this.coursApiConnectService.getCoursChapter(this.chapitreId).subscribe((data) => {
      this.chapitreData = data;
    });
  }
}
