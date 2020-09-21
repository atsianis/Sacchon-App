import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { DataTablesModule } from 'angular-datatables';
import { ChartsModule } from 'ng2-charts';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './content/header/header.component';
import { FooterComponent } from './content/footer/footer.component';
import { ContentComponent } from './content/content.component';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';

import { ReporterModule } from './reporter/reporter.module';

// medidatarepo imports
import { PatientEditProfileComponent } from './medi-data-repo/edit-profile/edit-profile.component';
import { AddPatientRecordComponent } from './medi-data-repo/add-patient-record/add-patient-record.component';
import { EditPatientRecordComponent } from './medi-data-repo/edit-patient-record/edit-patient-record.component';
import { PatientProfileComponent } from './medi-data-repo/profile/profile.component';

// doctoradvice imports
import { DoctorAdviceComponent } from './doctor-advice/doctor-advice.component';
import { PatientComponent } from './doctor-advice/patient/patient.component';
import { PatientListComponent } from './doctor-advice/patient/patient-list/patient-list.component';
import { ConsultComponent } from './doctor-advice/consult/consult.component';
import { PatientRecordComponent } from './medi-data-repo/patient-record/patient-record.component';
import { ConsultEditComponent } from './doctor-advice/consult-edit/consult-edit.component';
import { ConsultListComponent } from './doctor-advice/consult/consult-list/consult-list.component';
import { MediDataRepoComponent } from './medi-data-repo/medi-data-repo.component';
import { MediDataRepoModule } from './medi-data-repo/medi-data-repo.module';
import { DoctorAdviceModule } from './doctor-advice/doctor-advice.module';

@NgModule({
	declarations: [
		AppComponent,
		HeaderComponent,
		FooterComponent,
		ContentComponent,
		LoginComponent,
		SignupComponent,
		PatientEditProfileComponent,
		AddPatientRecordComponent,
		EditPatientRecordComponent,
		PatientProfileComponent,
		DoctorAdviceComponent,
		PatientComponent,
		PatientListComponent,
		ConsultComponent,
		PatientRecordComponent,
		ConsultEditComponent,
		ConsultListComponent,
		MediDataRepoComponent,
	],
	imports: [
		BrowserModule,
		AppRoutingModule,
		DataTablesModule,
		ChartsModule,
		ReporterModule,
		MediDataRepoModule,
		DoctorAdviceModule,
	],
	providers: [],
	bootstrap: [AppComponent]
})
export class AppModule { }
