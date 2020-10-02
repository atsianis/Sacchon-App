import { TestBed } from '@angular/core/testing';

import { DoctorAdviceGuard } from './doctor-advice.guard';

describe('DoctorAdviceGuard', () => {
  let guard: DoctorAdviceGuard;

  beforeEach(() => {
	TestBed.configureTestingModule({});
	guard = TestBed.inject(DoctorAdviceGuard);
  });

  it('should be created', () => {
	expect(guard).toBeTruthy();
  });
});
