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

@NgModule({
	declarations: [
		AppComponent,
		HeaderComponent,
		FooterComponent,
		ContentComponent,
		LoginComponent,
		SignupComponent,
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
