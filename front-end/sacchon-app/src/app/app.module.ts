import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { DataTablesModule } from 'angular-datatables';
import { ChartsModule } from 'ng2-charts';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './content/header/header.component';
import { FooterComponent } from './content/footer/footer.component';
import { ContentComponent } from './content/content.component';
import { LoginComponent } from './auth/login/login.component';

import { ReporterModule } from './reporter/reporter.module';
import { MediDataRepoModule } from './medi-data-repo/medi-data-repo.module';
import { DoctorAdviceModule } from './doctor-advice/doctor-advice.module';
import { MediDataRepoLoginComponent } from './auth/login/medi-data-repo-login/medi-data-repo-login.component';
import { DoctorAdviceLoginComponent } from './auth/login/doctor-advice-login/doctor-advice-login.component';
import { ReporterLoginComponent } from './auth/login/reporter-login/reporter-login.component';
import { PatientSignUpComponent } from './auth/signup/patient-sign-up/patient-sign-up.component';
import { DoctorSignUpComponent } from './auth/signup/doctor-sign-up/doctor-sign-up.component';

@NgModule({
	declarations: [
		AppComponent,
		HeaderComponent,
		FooterComponent,
		ContentComponent,
		LoginComponent,
		MediDataRepoLoginComponent,
		DoctorAdviceLoginComponent,
		ReporterLoginComponent,
		PatientSignUpComponent,
		DoctorSignUpComponent,
	],
	imports: [
		BrowserModule,
		AppRoutingModule,
		DataTablesModule,
		ChartsModule,
		ReporterModule,
		MediDataRepoModule,
		DoctorAdviceModule,
		ReactiveFormsModule,
		BrowserAnimationsModule,
		ToastrModule.forRoot(),
	],
	providers: [],
	bootstrap: [AppComponent]
})
export class AppModule { }
