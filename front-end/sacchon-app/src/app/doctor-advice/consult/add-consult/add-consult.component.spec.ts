import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddConsultComponent } from './add-consult.component';

describe('AddConsultComponent', () => {
  let component: AddConsultComponent;
  let fixture: ComponentFixture<AddConsultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddConsultComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddConsultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
