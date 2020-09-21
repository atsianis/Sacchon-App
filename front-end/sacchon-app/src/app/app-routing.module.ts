import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Main imports
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { ContentComponent } from './content/content.component';

// Medidatarepo imports
import { MediDataRepoComponent } from './medi-data-repo/medi-data-repo.component';
import { PatientProfileComponent } from './medi-data-repo/profile/profile.component';
import { PatientEditProfileComponent } from './medi-data-repo/edit-profile/edit-profile.component';
import { AddPatientRecordComponent } from './medi-data-repo/add-patient-record/add-patient-record.component';
import { EditPatientRecordComponent } from './medi-data-repo/edit-patient-record/edit-patient-record.component';
import { PatientRecordComponent } from './medi-data-repo/patient-record/patient-record.component';

// Reporter imports
import { CreateDoctorComponent } from './reporter/add-doctor/add-doctor.component';
import { InspectDoctorComponent } from './reporter/inspect-doctor/inspect-doctor.component';
import { InspectDoctorListComponent } from './reporter/inspect-doctor/inspect-doctor-list/inspect-doctor-list.component';
import { InspectPatientComponent } from './reporter/inspect-patient/inspect-patient.component';
import { InspectPatientListComponent } from './reporter/inspect-patient/inspect-patient-list/inspect-patient-list.component';
import { ReporterComponent } from './reporter/reporter.component';

// DoctorAdvice imports
import { DoctorAdviceComponent } from './doctor-advice/doctor-advice.component';
import { ProfileComponent } from './doctor-advice/profile/profile.component';
import { EditProfileComponent } from './doctor-advice/edit-profile/edit-profile.component';
import { PatientComponent } from './doctor-advice/patient/patient.component';
import { PatientListComponent } from './doctor-advice/patient/patient-list/patient-list.component';
import { ConsultComponent } from './doctor-advice/consult/consult.component';
import { ConsultEditComponent } from './doctor-advice/consult-edit/consult-edit.component';
import { ConsultListComponent } from './doctor-advice/consult/consult-list/consult-list.component';

const routes: Routes = [
	// Main routes
	{ path: '', component: ContentComponent },
	{ path: 'login', component: LoginComponent },
	{ path: 'signup', component: SignupComponent },
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }
