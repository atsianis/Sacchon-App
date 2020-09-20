import { Component, OnInit } from '@angular/core';
import { Doctors } from 'src/app/interfaces/doctors';
import { Patients } from 'src/app/interfaces/patients';
import { ReporterService } from '../reporter.service';

@Component({
	selector: 'sacchon-app-inspect-patient',
	templateUrl: './inspect-patient.component.html',
	styleUrls: ['./inspect-patient.component.scss']
})
export class InspectPatientComponent implements OnInit {

	doctors: Doctors[];
	patients: Patients[];

	constructor(private reporterService: ReporterService) { }
	dtOptions: DataTables.Settings = {};

	ngOnInit(): void {
		this.dtOptions = {
			pagingType: 'full_numbers'
		};
		this.getDoctor();
		this.getPatient();
	}

	getDoctor(): void {
		this.reporterService.getDoctors().subscribe(doctor => {
			this.doctors = doctor;
			console.log(this.doctors);
		});
	}

	getPatient(): void {
		this.reporterService.getPatients().subscribe(patient => {
			this.patients = patient;
			console.log(this.patients);
		});
	}
}
