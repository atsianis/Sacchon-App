import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultablePatientsComponent } from './consultable-patients.component';

describe('ConsultablePatientsComponent', () => {
  let component: ConsultablePatientsComponent;
  let fixture: ComponentFixture<ConsultablePatientsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConsultablePatientsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConsultablePatientsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
