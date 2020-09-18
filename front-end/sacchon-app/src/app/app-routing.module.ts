import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddDoctorComponent } from './chief-doctor/add-doctor/add-doctor.component';
import { ChiefDoctorComponent } from './chief-doctor/chief-doctor.component';
import { InspectDoctorComponent } from './chief-doctor/inspect-doctor/inspect-doctor.component';
import { InspectPatientComponent } from './chief-doctor/inspect-patient/inspect-patient.component';
import { ContentComponent } from './content/content.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';

const routes: Routes = [
	{ path: '', component: ContentComponent },
	{ path: 'login', component: LoginComponent },
	{ path: 'signup', component: SignupComponent },
	{ path: 'reporter', component: ChiefDoctorComponent, children: [
		{ path: 'doctors', component: InspectDoctorComponent },
		{ path: 'newdoctor', component: AddDoctorComponent },
		{ path: 'patients', component: InspectPatientComponent },
	]},
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }
