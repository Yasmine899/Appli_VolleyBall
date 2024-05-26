import { Injectable } from '@angular/core';
import { Question } from './question'; 
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Answer } from './answer';

@Injectable({
  providedIn: 'root'
})

export class QuestionService {

  readonly API_URL = "http://localhost:8080";
  readonly API_ENDPOINT_QUESTIONS = "/questions";
  readonly API_ENDPOINT_QUESTIONS_BY_CHAPTER = "/questionForChapter/";
  readonly answerApiUrl=  "http://localhost:8080/responseForQuestion/";


  
  constructor(private httpCLient: HttpClient) { }

  getQuestions() : Observable<Question[]> {
    return this.httpCLient.get<Question[]>(this.API_URL + this.API_ENDPOINT_QUESTIONS);
  }

  getQuestionsByChapter(chapter: number) : Observable<Question[]> {
    return this.httpCLient.get<Question[]>(this.API_URL + this.API_ENDPOINT_QUESTIONS_BY_CHAPTER + chapter);
  }

  getAnswers(questionId: number) : Observable<Answer[]> {
    return this.httpCLient.get<Answer[]>(`${this.answerApiUrl}${questionId}`);
  }
}
