import { TestBed } from '@angular/core/testing';

import { CoursApiConnectService } from './cours-api-connect.service';

describe('CoursApiConnectService', () => {
  let service: CoursApiConnectService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CoursApiConnectService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
