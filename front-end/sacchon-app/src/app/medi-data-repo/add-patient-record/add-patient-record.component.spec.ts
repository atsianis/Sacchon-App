import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddPatientRecordComponent } from './add-patient-record.component';

describe('AddPatientRecordComponent', () => {
  let component: AddPatientRecordComponent;
  let fixture: ComponentFixture<AddPatientRecordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddPatientRecordComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddPatientRecordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
