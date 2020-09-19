import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InspectDoctorListComponent } from './inspect-doctor-list.component';

describe('InspectDoctorListComponent', () => {
  let component: InspectDoctorListComponent;
  let fixture: ComponentFixture<InspectDoctorListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InspectDoctorListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InspectDoctorListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
