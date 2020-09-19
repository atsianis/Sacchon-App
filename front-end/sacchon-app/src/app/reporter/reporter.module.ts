import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CreateDoctorComponent } from './add-doctor/add-doctor.component';
import { InspectDoctorComponent } from './inspect-doctor/inspect-doctor.component';
import { InspectPatientComponent } from './inspect-patient/inspect-patient.component';

import { DataTablesModule } from 'angular-datatables';
import { InspectDoctorListComponent } from './inspect-doctor/inspect-doctor-list/inspect-doctor-list.component';
import { InspectPatientListComponent } from './inspect-patient/inspect-patient-list/inspect-patient-list.component';

@NgModule({
	declarations: [
		CreateDoctorComponent,
		InspectDoctorComponent,
		InspectPatientComponent,
		InspectDoctorListComponent,
		InspectPatientListComponent
	],
	imports: [
		CommonModule,
		DataTablesModule
	]
})
export class ReporterModule { }
