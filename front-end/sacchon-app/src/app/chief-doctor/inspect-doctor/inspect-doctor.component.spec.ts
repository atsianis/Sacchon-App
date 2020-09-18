import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InspectDoctorComponent } from './inspect-doctor.component';

describe('InspectDoctorComponent', () => {
  let component: InspectDoctorComponent;
  let fixture: ComponentFixture<InspectDoctorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InspectDoctorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InspectDoctorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
