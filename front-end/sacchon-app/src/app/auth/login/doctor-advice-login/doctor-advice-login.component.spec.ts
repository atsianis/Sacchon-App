import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorAdviceLoginComponent } from './doctor-advice-login.component';

describe('DoctorAdviceLoginComponent', () => {
  let component: DoctorAdviceLoginComponent;
  let fixture: ComponentFixture<DoctorAdviceLoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DoctorAdviceLoginComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DoctorAdviceLoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
