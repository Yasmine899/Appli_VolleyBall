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
    this.restart();
  }

  restart() {
    // Réinitialiser les variables et change les questions
    
    const chapterId = this.route.snapshot.paramMap.get('chapterId');
    if (chapterId) {
      console.log('Recuperation des questions pour le chapitre ' + chapterId);
      this.questionService.getQuestionsByChapter(+chapterId).subscribe((questions) => {
        console.log('Questions récupérées : ', questions);
        this.questions = questions;
        this.currentQuestion = this.questions[this.currentIndex];
        this.nbQuestions = this.questions.length;
        this.buttonLabel = "Valider";
        this.loadAnswers(this.currentQuestion?.questionId);
        console.log('this.questions après assignation : ', this.questions);
      });
    }
    this.currentIndex = 0;
    this.isfinished = false;
    this.score = 0;
    this.result = "";
    this.responseMode = false;

}


  loadAnswers(questionId: number | undefined): void {
    if (questionId !== undefined) {
      this.questionService.getAnswers(questionId).subscribe((answers) => {
        console.log('Réponses récupérées : ', answers);
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
      this.loadAnswers(this.currentQuestion?.questionId); // Charger les réponses pour la prochaine question
    } else {
      this.isfinished = true;
      this.currentQuestion = undefined;
      this.responseMode = false;
    }
  }

  validate(): void {
    let allCorrect = true;
    const inputs = document.querySelectorAll('input[type="checkbox"]');
    
    inputs.forEach((input, index) => {
      const isChecked = (input as HTMLInputElement).checked;
      const answer = this.answers[index];
      
      if (answer.reponseCorrecte && !isChecked) {
        allCorrect = false;
      } else if (!answer.reponseCorrecte && isChecked) {
        allCorrect = false;
      }
    });

    if (allCorrect) {
      this.score++;
      this.result = "Bonne réponse!";
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
    'correct': this.responseMode && this.answers[index].reponseCorrecte,
    'incorrect': this.responseMode && !this.answers[index].reponseCorrecte && checkbox.checked,
  };
  }
}
