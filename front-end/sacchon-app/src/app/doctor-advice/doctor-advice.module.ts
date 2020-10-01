import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ChartsModule } from 'ng2-charts';
import { DataTablesModule } from 'angular-datatables';
import { RouterModule } from '@angular/router';
import { ProfileComponent } from './profile/profile.component';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { PatientComponent } from './patient/patient.component';
import { PatientListComponent } from './patient/patient-list/patient-list.component';
import { ConsultComponent } from './consult/consult.component';
import { ConsultEditComponent } from './consult/consult-edit/consult-edit.component';
import { AddConsultComponent } from './consult/add-consult/add-consult.component';
import { DoctorAdviceGuard } from './doctor-advice.guard';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AvailablePatientsComponent } from './available-patients/available-patients.component';
import { ConsultablePatientsComponent } from './consultable-patients/consultable-patients.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';



@NgModule({
	declarations: [
		ProfileComponent,
		EditProfileComponent,
		PatientComponent,
		PatientListComponent,
		ConsultComponent,
		ConsultEditComponent,
		AddConsultComponent,
		AvailablePatientsComponent,
		ConsultablePatientsComponent
	],
	imports: [
		CommonModule,
		DataTablesModule,
		ChartsModule,
		HttpClientModule,
		ReactiveFormsModule,
		FormsModule,
		RouterModule.forChild([
			{ path: 'doctoradvice/profile', component: ProfileComponent, canActivate: [ DoctorAdviceGuard ] },
			{ path: 'doctoradvice/profile/edit', component: EditProfileComponent, canActivate: [ DoctorAdviceGuard ] },
			{ path: 'doctoradvice/patients', component: PatientComponent, canActivate: [ DoctorAdviceGuard ] },
			{ path: 'doctoradvice/patient/:id', component: PatientListComponent, canActivate: [ DoctorAdviceGuard ] },
			{ path: 'doctoradvice/consult/:patient_id', component: ConsultComponent, canActivate: [ DoctorAdviceGuard ] },
			{ path: 'doctoradvice/consult/:patient_id/add', component: AddConsultComponent, canActivate: [ DoctorAdviceGuard ] },
			{ path: 'doctoradvice/consult/:patient_id/:consultation_id/edit', component: ConsultEditComponent, canActivate: [ DoctorAdviceGuard ] },
			{ path: 'doctoradvice/availablepatients', component: AvailablePatientsComponent, canActivate: [ DoctorAdviceGuard ] },
			{ path: 'doctoradvice/consultrablepatients', component: ConsultablePatientsComponent, canActivate: [ DoctorAdviceGuard ] },
		]),
		NgbModule,
	],
	exports: [
		RouterModule
	]
})
export class DoctorAdviceModule { }
