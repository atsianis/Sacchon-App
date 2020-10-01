import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { PatientProfileComponent } from './profile/profile.component';
import { PatientEditProfileComponent } from './edit-profile/edit-profile.component';
import { AddPatientRecordComponent } from './add-patient-record/add-patient-record.component';
import { EditPatientRecordComponent } from './edit-patient-record/edit-patient-record.component';
import { DataTablesModule } from 'angular-datatables';
import { ChartsModule } from 'ng2-charts';
import { HttpClientModule } from '@angular/common/http';
import { MediDataRepoGuard } from './medi-data-repo.guard';
import { ControlContainer, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ConsultationsComponent } from './consultations/consultations.component';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';



@NgModule({
	declarations: [
		PatientProfileComponent,
		PatientEditProfileComponent,
		AddPatientRecordComponent,
		EditPatientRecordComponent,
		ConsultationsComponent,
	],
	imports: [
		BrowserModule,
		DataTablesModule,
		ChartsModule,
		ReactiveFormsModule,
		FormsModule,
		HttpClientModule,
		RouterModule.forChild([
			{ path: 'medidatarepo/profile', component: PatientProfileComponent, canActivate: [ MediDataRepoGuard ] },
			{ path: 'medidatarepo/profile/edit', component: PatientEditProfileComponent, canActivate: [ MediDataRepoGuard ] },
			{ path: 'medidatarepo/record/add', component: AddPatientRecordComponent, canActivate: [ MediDataRepoGuard ] },
			{ path: 'medidatarepo/record/:id/edit', component: EditPatientRecordComponent, canActivate: [ MediDataRepoGuard ] },
			{ path: 'medidatarepo/consultations', component: ConsultationsComponent, canActivate: [ MediDataRepoGuard ]}
		]),
		NgbModule,
	],
	exports: [
		RouterModule
	]
})
export class MediDataRepoModule { }
