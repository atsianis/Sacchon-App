import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ChiefDoctorComponent } from './chief-doctor/chief-doctor.component';
import { ContentComponent } from './content/content.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';

const routes: Routes = [
	{ path: '', component: ContentComponent },
	{ path: 'login', component: LoginComponent },
	{ path: 'signup', component: SignupComponent },
	{ path: 'chiefDoctor', component: ChiefDoctorComponent },
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }
