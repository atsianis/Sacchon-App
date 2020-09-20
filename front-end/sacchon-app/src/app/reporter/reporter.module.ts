import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ChartsModule } from 'ng2-charts';
import { DataTablesModule } from 'angular-datatables';

import { CreateDoctorComponent } from './add-doctor/add-doctor.component';
import { InspectDoctorComponent } from './inspect-doctor/inspect-doctor.component';
import { InspectPatientComponent } from './inspect-patient/inspect-patient.component';

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
		DataTablesModule,
		ChartsModule
	]
})
export class ReporterModule { }
