import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllDoctorsDbListComponent } from './all-doctors-db-list.component';

describe('AllDoctorsDbListComponent', () => {
  let component: AllDoctorsDbListComponent;
  let fixture: ComponentFixture<AllDoctorsDbListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllDoctorsDbListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AllDoctorsDbListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
