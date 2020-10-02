import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllDoctorsDbComponent } from './all-doctors-db.component';

describe('AllDoctorsDbComponent', () => {
  let component: AllDoctorsDbComponent;
  let fixture: ComponentFixture<AllDoctorsDbComponent>;

  beforeEach(async () => {
	await TestBed.configureTestingModule({
		declarations: [ AllDoctorsDbComponent ]
	})
	.compileComponents();
  });

  beforeEach(() => {
	fixture = TestBed.createComponent(AllDoctorsDbComponent);
	component = fixture.componentInstance;
	fixture.detectChanges();
  });

  it('should create', () => {
	expect(component).toBeTruthy();
  });
});
