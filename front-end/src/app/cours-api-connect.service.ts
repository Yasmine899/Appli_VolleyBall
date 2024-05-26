import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CoursApiConnectService {

  readonly API_URL = 'http://localhost:8080';

  readonly ENDPOINT_CHAPITRE = '/course/chapter';
  readonly ENDPOINT_SECTIONS = '/sections';
  readonly ENDPOINT_SECTION_CHAPITRES = '/chapter/section';

  constructor(private httpClient: HttpClient) { 

  }

  getCoursChapter (chapterId: number) {
    return this.httpClient.get(this.API_URL + this.ENDPOINT_CHAPITRE + '/' + chapterId);
  }

  getCoursSections () {
    return this.httpClient.get(this.API_URL + this.ENDPOINT_SECTIONS);
  }

  getSectionChapters (sectionId: number) {
    return this.httpClient.get(this.API_URL + this.ENDPOINT_SECTION_CHAPITRES + '/' + sectionId);
  }
}

