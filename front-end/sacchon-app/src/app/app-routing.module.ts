import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Main imports
import { LoginComponent } from './auth/login/login.component';
import { MediDataRepoLoginComponent } from './auth/login/medi-data-repo-login/medi-data-repo-login.component';
import { DoctorAdviceLoginComponent } from './auth/login/doctor-advice-login/doctor-advice-login.component';
import { ReporterLoginComponent } from './auth/login/reporter-login/reporter-login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { ContentComponent } from './content/content.component';
import { DoctorSignUpComponent } from './auth/signup/doctor-sign-up/doctor-sign-up.component';
import { PatientSignUpComponent } from './auth/signup/patient-sign-up/patient-sign-up.component';

const routes: Routes = [
	// Main routes
	{ path: '', component: ContentComponent },
	{ path: 'login', component: LoginComponent },
	{ path: 'login/medidatarepo', component: MediDataRepoLoginComponent },
	{ path: 'login/doctoradvice', component: DoctorAdviceLoginComponent },
	{ path: 'login/reporter', component: ReporterLoginComponent },
	{ path: 'signup/doctor', component: DoctorSignUpComponent },
	{ path: 'signup/patient', component: PatientSignUpComponent },
	{ path: 'signup', component: SignupComponent },
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }
