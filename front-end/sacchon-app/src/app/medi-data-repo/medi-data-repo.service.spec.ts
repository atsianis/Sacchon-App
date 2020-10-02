import { TestBed } from '@angular/core/testing';

import { MediDataRepoService } from './medi-data-repo.service';

describe('MediDataRepoService', () => {
  let service: MediDataRepoService;

  beforeEach(() => {
	TestBed.configureTestingModule({});
	service = TestBed.inject(MediDataRepoService);
  });

  it('should be created', () => {
	expect(service).toBeTruthy();
  });
});
