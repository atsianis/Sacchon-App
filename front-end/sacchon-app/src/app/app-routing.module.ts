import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Main imports
import { LoginComponent } from './auth/login/login.component';
import { MediDataRepoLoginComponent } from './auth/login/medi-data-repo-login/medi-data-repo-login.component';
import { DoctorAdviceLoginComponent } from './auth/login/doctor-advice-login/doctor-advice-login.component';
import { ReporterLoginComponent } from './auth/login/reporter-login/reporter-login.component';
import { ContentComponent } from './content/content.component';
import { PatientSignUpComponent } from './auth/signup/patient-sign-up/patient-sign-up.component';
import { AuthGuard } from './auth/auth.guard';
import { UnauthorizedComponent } from './generic/unauthorized/unauthorized.component';

const routes: Routes = [
	// Main routes
	{ path: '', component: ContentComponent, canActivate:[ AuthGuard ] },
	{ path: 'login', component: LoginComponent, canActivate:[ AuthGuard ] },
	{ path: 'login/medidatarepo', component: MediDataRepoLoginComponent, canActivate:[ AuthGuard ] },
	{ path: 'login/doctoradvice', component: DoctorAdviceLoginComponent, canActivate:[ AuthGuard ] },
	{ path: 'login/reporter', component: ReporterLoginComponent, canActivate:[ AuthGuard ] },
	{ path: 'signup', component: PatientSignUpComponent, canActivate:[ AuthGuard ] },
	{ path: 'unauthorized', component: UnauthorizedComponent },
]

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }
