import { TestBed } from '@angular/core/testing';

import { ChiefDoctorService } from './chief-doctor.service';

describe('ChiefDoctorService', () => {
  let service: ChiefDoctorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChiefDoctorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
