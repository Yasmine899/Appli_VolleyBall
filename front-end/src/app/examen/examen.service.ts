import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ExamenService {

  readonly API = "http://localhost:8080/questionForChapter/";

  constructor(private httpClient: HttpClient) { }

  getQuestionsFromChapter(chapter: string) {
    return this.httpClient.get(this.API+chapter);
  }
}
