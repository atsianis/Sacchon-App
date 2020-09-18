import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ContentComponent } from './content/content.component';
import { DatatableComponent } from './datatable/datatable.component';
import { LoginComponent } from './login/login.component';
import { AddDoctorComponent } from './reporter/add-doctor/add-doctor.component';
import { InspectDoctorComponent } from './reporter/inspect-doctor/inspect-doctor.component';
import { InspectPatientComponent } from './reporter/inspect-patient/inspect-patient.component';
import { ReporterComponent } from './reporter/reporter.component';
import { SignupComponent } from './signup/signup.component';

const routes: Routes = [
	{ path: '', component: ContentComponent },
	{ path: 'login', component: LoginComponent },
	{ path: 'signup', component: SignupComponent },
	{ path: 'reporter', component: ReporterComponent },
	{ path: 'reporter/doctors', component: InspectDoctorComponent },
	{ path: 'reporter/patients', component: InspectPatientComponent },
	{ path: 'reporter/adddoctor', component: AddDoctorComponent },
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }
