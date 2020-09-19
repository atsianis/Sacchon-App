import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MediDataRepoComponent } from './medi-data-repo.component';

describe('MediDataRepoComponent', () => {
  let component: MediDataRepoComponent;
  let fixture: ComponentFixture<MediDataRepoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MediDataRepoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MediDataRepoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
