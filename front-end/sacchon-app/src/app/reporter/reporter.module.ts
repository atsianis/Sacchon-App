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
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { AllPatientsDbComponent } from './all-patients-db/all-patients-db.component';
import { AllDoctorsDbComponent } from './all-doctors-db/all-doctors-db.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ReporterGuard } from './reporter.guard';
import { ConsultablePatientsComponent } from './consultable-patients/consultable-patients.component';

@NgModule({
	declarations: [
		InspectDoctorComponent,
		InspectPatientComponent,
		InspectDoctorListComponent,
		InspectPatientListComponent,
		InspectNonActiveComponent,
		EditProfileComponent,
		AllPatientsDbComponent,
		AllDoctorsDbComponent,
		ConsultablePatientsComponent,
	],
	imports: [
		CommonModule,
		DataTablesModule,
		ChartsModule,
		HttpClientModule,
		FormsModule,
		ReactiveFormsModule,
		RouterModule.forChild([
			{ path: 'reporter', component: ReporterComponent , canActivate: [ ReporterGuard ] },
			{ path: 'reporter/doctors', component: InspectDoctorComponent , canActivate: [ ReporterGuard ] },
			{ path: 'reporter/doctor/:id', component: InspectDoctorListComponent , canActivate: [ ReporterGuard ] },
			{ path: 'reporter/patients', component: InspectPatientComponent , canActivate: [ ReporterGuard ] },
			{ path: 'reporter/patient/:id', component: InspectPatientListComponent , canActivate: [ ReporterGuard ] },
			{ path: 'reporter/createdoctor', component: DoctorSignUpComponent , canActivate: [ ReporterGuard ] },
			{ path: 'reporter/inactives', component: InspectNonActiveComponent, canActivate: [ ReporterGuard ] },
			{ path: 'reporter/edit', component: EditProfileComponent, canActivate: [ ReporterGuard ] },
			{ path: 'reporter/consultablepatients', component: ConsultablePatientsComponent, canActivate: [ ReporterGuard ] },
			{ path: 'reporter/allpatientsDB', component: AllPatientsDbComponent, canActivate: [ ReporterGuard ] },
			{ path: 'reporter/alldoctorsDB', component: AllDoctorsDbComponent, canActivate: [ ReporterGuard ] }
		])
	],
	exports: [
		RouterModule
	]
})
export class ReporterModule { }
