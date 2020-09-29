import { TestBed } from '@angular/core/testing';

import { DoctorAdviceService } from './doctor-advice.service';

describe('DoctorAdviceService', () => {
  let service: DoctorAdviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DoctorAdviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
