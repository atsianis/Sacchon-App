import { TestBed } from '@angular/core/testing';

import { MediDataRepoGuard } from './medi-data-repo.guard';

describe('MediDataRepoGuard', () => {
  let guard: MediDataRepoGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(MediDataRepoGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
