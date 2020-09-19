import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditPatientRecordComponent } from './edit-patient-record.component';

describe('EditPatientRecordComponent', () => {
  let component: EditPatientRecordComponent;
  let fixture: ComponentFixture<EditPatientRecordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditPatientRecordComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditPatientRecordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
