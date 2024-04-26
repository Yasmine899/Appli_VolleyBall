import { Component, OnInit } from '@angular/core';
import { QuestionService } from './question.service';
import { Question } from './question'; // Importer l'interface ou la classe Question

@Component({
  selector: 'app-entrainement',
  template: `
    <div *ngIf="currentQuestion">
      <h2>{{ currentQuestion.statement }}</h2>
      <ul>
        <li *ngFor="let option of currentQuestion.options; let i = index">
          <label>
            <input type="checkbox" [checked]="isChecked(i)" (change)="toggleChecked(i)">
            {{ option }}
          </label>
        </li>
      </ul>
      <button (click)="nextQuestion()">Question suivante</button>
    </div>
  `
})
export class EntrainementComponent implements OnInit {
  questions!: Question[];
  currentIndex: number = 0;
  currentQuestion!: Question;

  constructor(private questionService: QuestionService) { }

  ngOnInit(): void {
    this.questions = this.questionService.getQuestions();
    this.currentQuestion = this.questions[this.currentIndex];
  }

  nextQuestion(): void {
    if (this.currentIndex < this.questions.length - 1) {
      this.currentIndex++;
      this.currentQuestion = this.questions[this.currentIndex];
    }
  }

  isChecked(optionIndex: number): boolean {
    return this.currentQuestion.correctAnswers.includes(optionIndex);
  }

  toggleChecked(optionIndex: number): void {
    const index = this.currentQuestion.correctAnswers.indexOf(optionIndex);
    if (index === -1) {
      this.currentQuestion.correctAnswers.push(optionIndex);
    } else {
      this.currentQuestion.correctAnswers.splice(index, 1);
    }
  }
}
