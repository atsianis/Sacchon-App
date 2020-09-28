import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

import { ChartsModule } from 'ng2-charts';
import { DataTablesModule } from 'angular-datatables';

import { InspectDoctorComponent } from './inspect-doctor/inspect-doctor.component';
import { InspectPatientComponent } from './inspect-patient/inspect-patient.component';

import { InspectDoctorListComponent } from './inspect-doctor/inspect-doctor-list/inspect-doctor-list.component';
import { InspectPatientListComponent } from './inspect-patient/inspect-patient-list/inspect-patient-list.component';
import { RouterModule } from '@angular/router';
import { ReporterComponent } from './reporter.component';
import { DoctorSignUpComponent } from '../auth/signup/doctor-sign-up/doctor-sign-up.component';
import { InspectNonActiveComponent } from './inspect-non-active/inspect-non-active.component';
import { ReporterGuard } from './reporter.guard';

@NgModule({
	declarations: [
		InspectDoctorComponent,
		InspectPatientComponent,
		InspectDoctorListComponent,
		InspectPatientListComponent,
		InspectNonActiveComponent
	],
	imports: [
		CommonModule,
		DataTablesModule,
		ChartsModule,
		HttpClientModule,
		RouterModule.forChild([
			{ path: 'reporter', component: ReporterComponent , canActivate: [ ReporterGuard ]},
			{ path: 'reporter/doctors', component: InspectDoctorComponent , canActivate: [ ReporterGuard ]},
			{ path: 'reporter/doctor/:id', component: InspectDoctorListComponent , canActivate: [ ReporterGuard ]},
			{ path: 'reporter/patients', component: InspectPatientComponent , canActivate: [ ReporterGuard ]},
			{ path: 'reporter/patient/:id', component: InspectPatientListComponent , canActivate: [ ReporterGuard ]},
			{ path: 'reporter/createdoctor', component: DoctorSignUpComponent , canActivate: [ ReporterGuard ]},
			{ path: 'reporter/inactives', component: InspectNonActiveComponent, canActivate: [ ReporterGuard ] }
		])
	],
	exports: [
		RouterModule
	]
})
export class ReporterModule { }
