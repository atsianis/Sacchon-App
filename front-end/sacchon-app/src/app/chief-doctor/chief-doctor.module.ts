import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChiefDoctorComponent } from './chief-doctor.component';



@NgModule({
	declarations: [ChiefDoctorComponent],
	imports: [
		CommonModule
	],
	exports: [
		ChiefDoctorComponent
	]
})
export class ChiefDoctorModule { }
