import { Component } from '@angular/core';
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
  public questions: Question[] = [];

  constructor(private questionService: QuestionService) { }

  ngOnInit(): void {
    this.questions = this.questionService.getQuestions();
  }

  /*print_exam(examen) {
    let printContents = document.getElementById(examen).innerHTML;
    let originalContents = document.body.innerHTML;

    document.body.innerHTML = printContents;

    window.print();

    document.body.innerHTML = originalContents;
  }*/
}
