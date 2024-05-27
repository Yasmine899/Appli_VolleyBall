import { Component } from '@angular/core';
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
  public questions: ExamenQuestion[] = [];
  allQuestions: ExamenQuestion[] = [];
  chapters: number[] = [];
  examLength: number = 40;
  chapterLengths: number[] = [];
  elemOpts: string[] = []

  constructor(private examenService: ExamenService) { }

  ngOnInit(): void {
    // Get questions from database
    this.examenService.getAllQuestions().subscribe((data: any) => {
      this.allQuestions = data;
      for (let i = 0; i < data.length; i++) {
        if (!(this.allQuestions[i].chapitre in this.chapters)) {
          this.chapters.push(this.allQuestions[i].chapitre);
        }
      }
      // Remove duplicates
      this.chapters = [...new Set(this.chapters)];
      this.new_exam();
    })
  }

  new_exam() {
    this.questions = [];

    // Setting all chapter lengths to the quotient 
    let quotient = Math.floor(this.examLength/this.chapters.length);
    for (let i = 0; i < this.chapters.length; i++) {
      this.chapterLengths.push(quotient);
    } 

    // Dividing the rest of the exam questions over the chapters
    for (let i = 0; i < this.examLength % this.chapters.length; i++) {
      this.chapterLengths[i]++;
    }

    // Extracts and populates the exam sheet with questions from the database from each chapter
    for (let i = 0; i < this.chapters.length; i++) {
      this.examenService.getQuestionsFromChapter(this.chapters[i]).subscribe((data: any) => {
        let available = data;
        for (let k = 0; k < this.chapterLengths[i]; k++) {
          let random = Math.floor(Math.random()*available.length);

          // Find the options to the newly added question
          this.examenService.getQuestionOptions(available[random].questionId).subscribe((optionsData: any) => {
            
            let options: string[] = optionsData.map((option: any) => option.reponseText);

            let elem = {
              chapitre: available[random].chapitre, 
              questionId: available[random].questionId,
              questionText: available[random].questionText,
              questionScore: available[random].questionScore,
              options: options
            }

            // Add the options and push the constructed question to the list of questions used in the exam 
            this.questions.push(elem);

            console.log("this.questions: ");
            console.log(this.questions[this.questions.length-1]);

            // Remove the newly added question from the available array
            available.splice(random,1);
            this.elemOpts.splice(0,this.elemOpts.length);
          })
        }
      })
    }
  }

  print_exam() {
    console.log("print");
    let title = document.title;
    document.title = "Examen Final";
    window.print();
    document.title = title;
  }
}