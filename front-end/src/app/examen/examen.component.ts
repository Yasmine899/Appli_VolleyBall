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

  /*new_exam(examen) {
  }*/

  print_exam() {
    let printContents = this.exam_preview.nativeElement.innerHTML;
    let originalContents = document.body.innerHTML;

     document.body.innerHTML = printContents;

     window.print();

     document.body.innerHTML = originalContents;
  }
}
