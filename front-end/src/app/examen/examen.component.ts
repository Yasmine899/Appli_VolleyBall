import { Component, ElementRef, ViewChild, AfterViewInit } from '@angular/core';
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
export class ExamenComponent implements AfterViewInit {
  @ViewChild('exam_preview') exam_preview: ElementRef | undefined;
  public questions: Question[] = [];

  constructor(private questionService: QuestionService) { }

  ngAfterViewInit() {
    if (this.exam_preview) {
      console.log(this.exam_preview.nativeElement.innerHTML);
    }
  }

  ngOnInit(): void {
    this.questions = this.questionService.getQuestions();
  }

  print_exam() {
    if (this.exam_preview) {
      this.exam_preview.nativeElement.innerHTML.print();
    }
  }
}
