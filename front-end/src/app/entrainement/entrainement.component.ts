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
  isfinished: boolean = false;
  score: number = 0;
  result!: string;
  buttonLabel!: string;
  responseMode: boolean = false;

  constructor(private questionService: QuestionService) { }

  ngOnInit(): void {
    this.questions = this.questionService.getQuestions();
    this.currentQuestion = this.questions[this.currentIndex];
    this.nbQuestions = this.questions.length;
    this.buttonLabel = "Valider";
  }

  nextQuestion(): void {
    if (this.currentIndex < this.questions.length - 1) {
      this.currentIndex++;
      this.currentQuestion = this.questions[this.currentIndex];
      this.buttonLabel = "Valider";
      this.responseMode = false;
    } else {
      this.isfinished = true;
      this.currentQuestion = undefined;
      this.responseMode = false;
    }
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
      this.result = "Bonne réponse!";
      this.score++;
    } else {
      this.result = "Mauvaise réponse!";
    }

    // Changer le libellé du bouton
    if (this.currentIndex === this.nbQuestions - 1) {
      this.buttonLabel = "Terminer";
    } else {
      this.buttonLabel = "Question suivante";
    }

    // Passer en mode réponse
    this.responseMode = true;
  }

  toggleButton() {
    if (this.buttonLabel === "Valider") {
      this.validate();
    } else {
      this.nextQuestion();
    }
  }

    getOptionClasses(index: number): any {
      const checkboxes = document.querySelectorAll('input[type="checkbox"]');
      const checkbox = checkboxes[index] as HTMLInputElement;
    return {
      'correct': this.responseMode && this.currentQuestion?.correctAnswers.includes(index),
      'incorrect': this.responseMode && !this.currentQuestion?.correctAnswers.includes(index) && checkbox.checked,
    };
  }
}

