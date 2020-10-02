import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InspectPatientComponent } from './inspect-patient.component';

describe('InspectPatientComponent', () => {
  let component: InspectPatientComponent;
  let fixture: ComponentFixture<InspectPatientComponent>;

  beforeEach(async () => {
	await TestBed.configureTestingModule({
		declarations: [ InspectPatientComponent ]
	})
	.compileComponents();
  });

  beforeEach(() => {
	fixture = TestBed.createComponent(InspectPatientComponent);
	component = fixture.componentInstance;
	fixture.detectChanges();
  });

  it('should create', () => {
	expect(component).toBeTruthy();
  });
});
