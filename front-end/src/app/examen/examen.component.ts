import { Component, ElementRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ExamenQuestion } from './examenQuestion';
import { ExamenService } from './examen.service';

@Component({
  selector: 'app-examen',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './examen.component.html',
  styleUrl: './examen.component.scss',
  providers: [ExamenService]
})

export class ExamenComponent {
  @ViewChild('exam_preview') exam_preview: ElementRef;
  public questions: ExamenQuestion[] = [];

  constructor(private examenService: ExamenService) { }

  ngOnInit(): void {
    // To do: get questions from database
    this.examenService.getQuestionsFromChapter("1").subscribe((data) => {
      //this.questions = data;
      var q = data;
      console.log(q);
    })
  }

  new_exam() {
  }

  print_exam() {
    console.log("print");
    let title = document.title;
    document.title = "Examen Final";
    window.print();
    document.title = title;
  }
}
