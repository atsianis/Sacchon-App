import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorAdviceComponent } from './doctor-advice.component';

describe('DoctorAdviceComponent', () => {
  let component: DoctorAdviceComponent;
  let fixture: ComponentFixture<DoctorAdviceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DoctorAdviceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DoctorAdviceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
