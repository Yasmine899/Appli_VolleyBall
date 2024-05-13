import { Component, ElementRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { QuestionService } from '../question/question.service';
import { Question } from '../question/question';

@Component({
  selector: 'app-examen',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './examen.component.html',
  styleUrl: './examen.component.scss'
})
export class ExamenComponent {
  @ViewChild('exam_preview') exam_preview: ElementRef;
  public questions: Question[] = [];

  constructor(private questionService: QuestionService) { }

  ngOnInit(): void {
    // To do: get questions from database
    this.questions = this.questionService.getQuestions();
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
