import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReporterRoutingModule } from './reporter-routing.module';
import { AddDoctorComponent } from './add-doctor/add-doctor.component';
import { InspectDoctorComponent } from './inspect-doctor/inspect-doctor.component';
import { InspectPatientComponent } from './inspect-patient/inspect-patient.component';
import { ReporterComponent } from './reporter.component';


@NgModule({
	declarations: [AddDoctorComponent, InspectDoctorComponent, InspectPatientComponent, ReporterComponent],
	imports: [
		CommonModule,
		ReporterRoutingModule
	]
})
export class ReporterModule { }
