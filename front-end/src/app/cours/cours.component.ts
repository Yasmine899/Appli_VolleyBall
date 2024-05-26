import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CoursApiConnectService } from '../cours-api-connect.service';

@Component({
  selector: 'app-cours',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './cours.component.html',
  styleUrl: './cours.component.scss',
  providers: [CoursApiConnectService]
})
export class CoursComponent implements OnInit{
  // Titles for the sections of the course, should get this from database
  titles: string[] = [];

  constructor(private coursApiConnectService: CoursApiConnectService){}
  
  ngOnInit(): void {
    console.log("Getting course section titles from API...");
    this.coursApiConnectService.getCoursSections().subscribe((coursSectionsData : any) => {
      for (let i = 0; i < coursSectionsData.length; i++) {
        this.titles.push(coursSectionsData[i].description);
      }
    });
  }
}
