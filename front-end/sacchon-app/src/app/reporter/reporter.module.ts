import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AddDoctorComponent } from './add-doctor/add-doctor.component';
import { InspectDoctorComponent } from './inspect-doctor/inspect-doctor.component';
import { InspectPatientComponent } from './inspect-patient/inspect-patient.component';

import { DataTablesModule } from 'angular-datatables';



@NgModule({
	declarations: [AddDoctorComponent, InspectDoctorComponent, InspectPatientComponent],
	imports: [
		CommonModule,
		DataTablesModule
	]
})
export class ReporterModule { }
