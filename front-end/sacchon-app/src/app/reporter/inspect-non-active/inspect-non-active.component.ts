import { Component, OnInit } from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';
import { Doctors } from 'src/app/interfaces/doctors';
import { Patients } from 'src/app/interfaces/patients';
import { ReporterService } from '../reporter.service';

@Component({
	selector: 'sacchon-app-inspect-non-active',
	templateUrl: './inspect-non-active.component.html',
	styleUrls: ['./inspect-non-active.component.scss']
})
export class InspectNonActiveComponent implements OnInit {

	doctors: Doctors[];
	patients: Patients[];
	dtElement: DataTableDirective;
	dtDoctorOptions: DataTables.Settings = {};
	dtDoctorTrigger: Subject<any> = new Subject();
	dtPatientOptions: DataTables.Settings = {};
	dtPatientTrigger: Subject<any> = new Subject();

	constructor(private reporterService: ReporterService) { }

	ngOnInit(): void {
		this.doctors = [];
		this.patients = [];
		this.getInactiveDoctors();
		this.getInactivePatients();
		this.dtDoctorOptions = {
			pagingType: 'full_numbers',
			pageLength: 10,			order: [0, 'asc'],
		};
		this.dtPatientOptions = {
			pagingType: 'full_numbers',
			pageLength: 10,			order: [0, 'asc'],
		};
	}

	getInactiveDoctors(): void {
		this.reporterService.getInactiveDoctors().subscribe(doctors => {
			this.doctors = doctors.data;
			this.dtDoctorTrigger.next();
		}, (err) => {
			console.log('-----> err', err);
		});
	}

	getInactivePatients(): void {
		this.reporterService.getInactivePatients().subscribe(patients => {
			this.patients = patients.data;
			this.dtPatientTrigger.next();
		}, (err) => {
			console.log('-----> err', err);
		});
	}

	lastActive(date: number): string {
		return this.reporterService.lastActive(date);
	}
}
