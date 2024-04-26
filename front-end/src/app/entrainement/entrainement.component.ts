import { Component, OnInit } from '@angular/core';
import { QuestionService } from '../question/question.service';
import { Question } from '../question/question';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-entrainement',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './entrainement.component.html',
  styleUrl: './entrainement.component.scss'
})
export class EntrainementComponent implements OnInit{
restart() {
throw new Error('Method not implemented.');
}
  questions: Question[] = [];
  currentIndex: number = 0;
  nbQuestions!: number;
  currentQuestion: Question | undefined;
  progress: number | undefined;
  isfinished: boolean = false;
  score: number = 0;

  constructor(private questionService: QuestionService) { }

  ngOnInit(): void {
    this.questions = this.questionService.getQuestions();
    this.currentQuestion = this.questions[this.currentIndex];
    this.nbQuestions = this.questions.length;
    this.calculateProgress();
  }

  nextQuestion(): void {
    if (this.currentIndex < this.questions.length - 1) {
      this.currentIndex++;
      this.currentQuestion = this.questions[this.currentIndex];
      this.calculateProgress();
    } else {
      this.isfinished = true;
      this.currentQuestion = undefined;
    }
  }

  toggleChecked(optionIndex: number): void {
    const index = this.currentQuestion?.correctAnswers.indexOf(optionIndex) || -1;
    if (index === -1) {
      this.currentQuestion?.correctAnswers.push(optionIndex);
    } else {
      this.currentQuestion?.correctAnswers.splice(index, 1);
    }
  }

  calculateProgress(): void {
    this.progress = Math.round((this.currentIndex + 1) / this.questions.length * 100);
  }

  validate(): void {
  
  let selectedOptions: number[] = [];
  let correctOptions: number[] = this.currentQuestion?.correctAnswers || [];

  // Récupérer les indices des options sélectionnées par l'utilisateur
  let checkboxes = document.querySelectorAll('input[type="checkbox"]');
  checkboxes.forEach((checkbox: any, index: number) => {
    if (checkbox.checked) {
      selectedOptions.push(index);
    }
  });
  console.log(selectedOptions);

  // Vérifier si les réponses sélectionnées par l'utilisateur sont correctes
  let isCorrect: boolean = true;
  if (selectedOptions.length !== correctOptions.length) {
    isCorrect = false;
  } else {
    for (let i = 0; i < selectedOptions.length; i++) {
      if (!correctOptions.includes(selectedOptions[i])) {
        isCorrect = false;
        break;
      }
    }
  }

  // Afficher un message approprié à l'utilisateur
  if (isCorrect) {
    alert("Bonne réponse !");
    this.score++;
  } else {
    if (correctOptions.length === 1) {
      alert("Mauvaise réponse. La bonne réponse est : " + this.currentQuestion?.options[correctOptions[0]]);
    } else {
      let correctAnswers: string[] = correctOptions.map((index: number) => this.currentQuestion?.options[index] || "");
      alert("Mauvaise réponse. Les bonnes réponses sont : " + correctAnswers.join(", "));
    }
  }

  this.nextQuestion();
  }
  
}

