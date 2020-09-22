import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InspectPatientListComponent } from './inspect-patient-list.component';

describe('InspectPatientListComponent', () => {
  let component: InspectPatientListComponent;
  let fixture: ComponentFixture<InspectPatientListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InspectPatientListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InspectPatientListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
