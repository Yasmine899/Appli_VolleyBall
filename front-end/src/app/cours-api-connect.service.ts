import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CoursApiConnectService {

  readonly API_URL = 'http://localhost:8080';

  readonly ENDPOINT_COURS = '/course/chapter';

  constructor(private httpClient: HttpClient) { 

  }

  getCoursChapter (chapterId: number) {
    return this.httpClient.get(this.API_URL + this.ENDPOINT_COURS + '/' + chapterId);
  }
}

