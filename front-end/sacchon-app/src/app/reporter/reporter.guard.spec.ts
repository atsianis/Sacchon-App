import { TestBed } from '@angular/core/testing';

import { ReporterGuard } from './reporter.guard';

describe('ReporterGuard', () => {
  let guard: ReporterGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(ReporterGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
