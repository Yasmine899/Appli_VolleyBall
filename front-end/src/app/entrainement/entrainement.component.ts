import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { QuestionService } from '../question/question.service';
import { Question } from '../question/question';
import { Answer } from '../question/answer'; // Import de la nouvelle interface Answer
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-entrainement',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './entrainement.component.html',
  styleUrls: ['./entrainement.component.scss'],
  providers: [QuestionService]
})
export class EntrainementComponent implements OnInit {
restart() {
throw new Error('Method not implemented.');
}

  questions: Question[] = [];
  answers: Answer[] = [];
  currentIndex: number = 0;
  nbQuestions!: number;
  currentQuestion: Question | undefined;
  isfinished: boolean = false;
  score: number = 0;
  result!: string;
  buttonLabel!: string;
  responseMode: boolean = false;

  constructor(
    private questionService: QuestionService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const chapterId = this.route.snapshot.paramMap.get('chapterId');
    if (chapterId) {
      console.log('Recuperation des questions pour le chapitre ' + chapterId);
      this.questionService.getQuestionsByChapter(+chapterId).subscribe((questions) => {
        console.log('Questions récupérées : ', questions);
        this.questions = questions;
        this.currentQuestion = this.questions[this.currentIndex];
        this.nbQuestions = this.questions.length;
        this.buttonLabel = "Valider";
        console.log('this.questions après assignation : ', this.questions);
      });
    }
  }

  loadAnswers(): void {
    if (this.currentQuestion) {
      this.questionService.getAnswers(this.currentQuestion.questionId).subscribe((answers: Answer[]) => {
        this.answers = answers;
      });
    }
  }

  nextQuestion(): void {
    if (this.currentIndex < this.questions.length - 1) {
      this.currentIndex++;
      this.currentQuestion = this.questions[this.currentIndex];
      this.buttonLabel = "Valider";
      this.responseMode = false;
      this.loadAnswers(); // Charger les réponses pour la prochaine question
    } else {
      this.isfinished = true;
      this.currentQuestion = undefined;
      this.responseMode = false;
    }
  }

  validate(): void {
    let selectedOptions: number[] = [];
    let correctOptions: number[] = this.answers.filter(a => a.isCorrect).map(a => a.responseId);

    let checkboxes = document.querySelectorAll('input[type="checkbox"]');
    checkboxes.forEach((checkbox: any, index: number) => {
      if (checkbox.checked) {
        selectedOptions.push(this.answers[index].responseId);
      }
    });
    console.log(selectedOptions);

    let isCorrect: boolean = selectedOptions.length === correctOptions.length && 
                             selectedOptions.every(val => correctOptions.includes(val));

    if (isCorrect) {
      this.result = "Bonne réponse!";
      this.score++;
    } else {
      this.result = "Mauvaise réponse!";
    }

    if (this.currentIndex === this.nbQuestions - 1) {
      this.buttonLabel = "Terminer";
    } else {
      this.buttonLabel = "Question suivante";
    }

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
    'correct': this.responseMode && this.answers[index].isCorrect,
    'incorrect': this.responseMode && !this.answers[index].isCorrect && checkbox.checked,
  };
  }
}
