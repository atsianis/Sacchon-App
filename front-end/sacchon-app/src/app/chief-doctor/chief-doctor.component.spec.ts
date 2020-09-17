import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChiefDoctorComponent } from './chief-doctor.component';

describe('ChiefDoctorComponent', () => {
  let component: ChiefDoctorComponent;
  let fixture: ComponentFixture<ChiefDoctorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChiefDoctorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChiefDoctorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
