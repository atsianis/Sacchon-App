import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MediDataRepoLoginComponent } from './medi-data-repo-login.component';

describe('MediDataRepoLoginComponent', () => {
  let component: MediDataRepoLoginComponent;
  let fixture: ComponentFixture<MediDataRepoLoginComponent>;

  beforeEach(async () => {
	await TestBed.configureTestingModule({
		declarations: [ MediDataRepoLoginComponent ]
	})
	.compileComponents();
  });

  beforeEach(() => {
	fixture = TestBed.createComponent(MediDataRepoLoginComponent);
	component = fixture.componentInstance;
	fixture.detectChanges();
  });

  it('should create', () => {
	expect(component).toBeTruthy();
  });
});
