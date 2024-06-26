import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent implements OnInit{

  showCourses!: boolean;


  courses: string[] = ['Installations, équipements et participants ', 'Formule de jeu et action de jeu', 'Interruptions, retard de jeu et intervalles, le libéro et conduite des participants', 'Les arbitres, leurs responsabilités et les gestes officiels ?'];
by: any;

  ngOnInit(): void {
    this.showCourses=false;
  }

  showCoursesDropdown(){
    console.log("over!!!\n");
    this.showCourses=!this.showCourses;
  }

}

