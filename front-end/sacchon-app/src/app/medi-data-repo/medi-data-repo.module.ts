import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { PatientProfileComponent } from './profile/profile.component';
import { PatientEditProfileComponent } from './edit-profile/edit-profile.component';
import { PatientRecordComponent } from './patient-record/patient-record.component';
import { AddPatientRecordComponent } from './add-patient-record/add-patient-record.component';
import { EditPatientRecordComponent } from './edit-patient-record/edit-patient-record.component';
import { DataTablesModule } from 'angular-datatables';
import { ChartsModule } from 'ng2-charts';
import { HttpClientModule } from '@angular/common/http';
import { MediDataRepoGuard } from './medi-data-repo.guard';
import { FormGroup } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';



@NgModule({
	declarations: [
		PatientProfileComponent,
		PatientEditProfileComponent,
		PatientRecordComponent,
		AddPatientRecordComponent,
		EditPatientRecordComponent,
		FormGroup
	],
	imports: [
		BrowserModule,
		DataTablesModule,
		ChartsModule,
		HttpClientModule,
		RouterModule.forChild([
			{ path: 'medidatarepo/profile', component: PatientProfileComponent, canActivate: [ MediDataRepoGuard ] },
			{ path: 'medidatarepo/profile/edit', component: PatientEditProfileComponent, canActivate: [ MediDataRepoGuard ] },
			{ path: 'medidatarepo/record', component: PatientRecordComponent, canActivate: [ MediDataRepoGuard ] },
			{ path: 'medidatarepo/record/add', component: AddPatientRecordComponent, canActivate: [ MediDataRepoGuard ] },
			{ path: 'medidatarepo/record/edit', component: EditPatientRecordComponent, canActivate: [ MediDataRepoGuard ] },
		])
	],
	exports: [
		RouterModule
	]
})
export class MediDataRepoModule { }
