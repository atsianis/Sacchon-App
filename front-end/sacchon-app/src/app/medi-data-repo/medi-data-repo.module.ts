import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MediDataRepoComponent } from './medi-data-repo.component';
import { PatientProfileComponent } from './profile/profile.component';
import { PatientEditProfileComponent } from './edit-profile/edit-profile.component';
import { PatientRecordComponent } from './patient-record/patient-record.component';
import { AddPatientRecordComponent } from './add-patient-record/add-patient-record.component';
import { EditPatientRecordComponent } from './edit-patient-record/edit-patient-record.component';
import { DataTablesModule } from 'angular-datatables';
import { ChartsModule } from 'ng2-charts';
import { HttpClientModule } from '@angular/common/http';



@NgModule({
	declarations: [],
	imports: [
		CommonModule,
		DataTablesModule,
		ChartsModule,
		HttpClientModule,
		RouterModule.forChild([
			{ path: 'medidatarepo', component: MediDataRepoComponent },
			{ path: 'medidatarepo/profile', component: PatientProfileComponent },
			{ path: 'medidatarepo/profile/edit', component: PatientEditProfileComponent },
			{ path: 'medidatarepo/record', component: PatientRecordComponent },
			{ path: 'medidatarepo/record/add', component: AddPatientRecordComponent },
			{ path: 'medidatarepo/record/edit', component: EditPatientRecordComponent },
		])
	],
	exports: [
		RouterModule
	]
})
export class MediDataRepoModule { }
