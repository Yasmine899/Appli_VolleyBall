import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ExamenService {

  readonly API_CHAPTER = "http://localhost:8080/questionForChapter/";

  readonly API_OPTIONS = "http://localhost:8080/responseForQuestion/";

  readonly API_ALL_QUESTIONS = "http://localhost:8080/questions";

  constructor(private httpClient: HttpClient) { }

  getQuestionsFromChapter(chapter: number) {
    return this.httpClient.get(this.API_CHAPTER + chapter);
  }

  getQuestionOptions(questionId: number) {
    return this.httpClient.get(this.API_OPTIONS + questionId);
  }

  getAllQuestions() {
    return this.httpClient.get(this.API_ALL_QUESTIONS);
  }
}
