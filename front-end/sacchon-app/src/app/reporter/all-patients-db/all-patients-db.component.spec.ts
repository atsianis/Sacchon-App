import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllPatientsDbComponent } from './all-patients-db.component';

describe('AllPatientsDbComponent', () => {
  let component: AllPatientsDbComponent;
  let fixture: ComponentFixture<AllPatientsDbComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllPatientsDbComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AllPatientsDbComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
