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
import { DatatableComponent } from './datatable/datatable.component';
import { ReporterModule } from './reporter/reporter.module';

@NgModule({
	declarations: [
		AppComponent,
		HeaderComponent,
		FooterComponent,
		ContentComponent,
		LoginComponent,
		SignupComponent,
		DatatableComponent
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
