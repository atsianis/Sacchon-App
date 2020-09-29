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
import { AllDoctorsDbListComponent } from './all-doctors-db/all-doctors-db-list/all-doctors-db-list.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

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
		AllDoctorsDbListComponent
	],
	imports: [
		CommonModule,
		DataTablesModule,
		ChartsModule,
		HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
		RouterModule.forChild([
			{ path: 'reporter', component: ReporterComponent },
			{ path: 'reporter/doctors', component: InspectDoctorComponent },
			{ path: 'reporter/doctor/:id', component: InspectDoctorListComponent },
			{ path: 'reporter/patients', component: InspectPatientComponent },
			{ path: 'reporter/patient/:id', component: InspectPatientListComponent },
			{ path: 'reporter/createdoctor', component: DoctorSignUpComponent },
			{ path: 'reporter/inactives', component: InspectNonActiveComponent },
			{ path: 'reporter/edit', component: EditProfileComponent},
			{ path: 'reporter/allpatientsDB', component: AllPatientsDbComponent},
			{ path: 'reporter/alldoctorsDB', component: AllDoctorsDbComponent}
		])
	],
	exports: [
		RouterModule
	]
})
export class ReporterModule { }
