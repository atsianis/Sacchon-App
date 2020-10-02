import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InspectNonActiveComponent } from './inspect-non-active.component';

describe('InspectNonActiveComponent', () => {
  let component: InspectNonActiveComponent;
  let fixture: ComponentFixture<InspectNonActiveComponent>;

  beforeEach(async () => {
	await TestBed.configureTestingModule({
		declarations: [ InspectNonActiveComponent ]
	})
	.compileComponents();
  });

  beforeEach(() => {
	fixture = TestBed.createComponent(InspectNonActiveComponent);
	component = fixture.componentInstance;
	fixture.detectChanges();
  });

  it('should create', () => {
	expect(component).toBeTruthy();
  });
});
