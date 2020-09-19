import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { DataTablesModule } from 'angular-datatables';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { ContentComponent } from './content/content.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { ReporterModule } from './reporter/reporter.module';
import { EditProfileComponent } from './medi-data-repo/edit-profile/edit-profile.component';
import { AddPatientRecordComponent } from './medi-data-repo/add-patient-record/add-patient-record.component';
import { EditPatientRecordComponent } from './medi-data-repo/edit-patient-record/edit-patient-record.component';
import { ProfileComponent } from './medi-data-repo/profile/profile.component';
import { DoctorAdviceComponent } from './doctor-advice/doctor-advice.component';
import { PatientComponent } from './doctor-advice/patient/patient.component';
import { PatientListComponent } from './doctor-advice/patient/patient-list/patient-list.component';
import { ConsultComponent } from './doctor-advice/consult/consult.component';

@NgModule({
	declarations: [
		AppComponent,
		HeaderComponent,
		FooterComponent,
		ContentComponent,
		LoginComponent,
		SignupComponent,
		EditProfileComponent,
		AddPatientRecordComponent,
		EditPatientRecordComponent,
		ProfileComponent,
		DoctorAdviceComponent,
		PatientComponent,
		PatientListComponent,
		ConsultComponent,
	],
	imports: [
		BrowserModule,
		AppRoutingModule,
		ReporterModule,
		DataTablesModule
	],
	providers: [],
	bootstrap: [AppComponent]
})
export class AppModule { }
