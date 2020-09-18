import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChiefDoctorComponent } from './chief-doctor.component';
import { InspectDoctorComponent } from './inspect-doctor/inspect-doctor.component';
import { AddDoctorComponent } from './add-doctor/add-doctor.component';
import { InspectPatientComponent } from './inspect-patient/inspect-patient.component';



@NgModule({
	declarations: [
		ChiefDoctorComponent,
		InspectDoctorComponent,
		AddDoctorComponent,
		InspectPatientComponent
	],
	imports: [
		CommonModule
	],
	exports: [
		ChiefDoctorComponent,
		InspectDoctorComponent,
		AddDoctorComponent,
		InspectPatientComponent
	]
})
export class ChiefDoctorModule { }
