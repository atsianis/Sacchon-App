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
import { MediDataRepoModule } from './medi-data-repo/medi-data-repo.module';
import { DoctorAdviceModule } from './doctor-advice/doctor-advice.module';
import { MediDataRepoLoginComponent } from './auth/login/medi-data-repo-login/medi-data-repo-login.component';
import { DoctorAdviceLoginComponent } from './auth/login/doctor-advice-login/doctor-advice-login.component';
import { ReporterLoginComponent } from './auth/login/reporter-login/reporter-login.component';

@NgModule({
	declarations: [
		AppComponent,
		HeaderComponent,
		FooterComponent,
		ContentComponent,
		LoginComponent,
		SignupComponent,
		MediDataRepoLoginComponent,
		DoctorAdviceLoginComponent,
		ReporterLoginComponent,
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
