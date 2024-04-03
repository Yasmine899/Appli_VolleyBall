import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [],
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

  toggleCoursesDropdown() {
    if (this.showCourses==false){
      this.showCourses=true;
    } else {
      this.showCourses=false;
    }
  }

}
