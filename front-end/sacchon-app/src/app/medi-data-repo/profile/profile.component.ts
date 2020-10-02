import { Component, OnInit } from '@angular/core';
import * as moment from 'moment';
import { MediDataRepoService } from '../medi-data-repo.service';
import { PatientRecords } from 'src/app/interfaces/patient-records';
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';

@Component({
	selector: 'sacchon-app-profile',
	templateUrl: './profile.component.html',
	styleUrls: ['./profile.component.scss']
})
export class PatientProfileComponent implements OnInit {

	constructor(private patientService: MediDataRepoService) { }

	firstName: string = sessionStorage.getItem('firstName');
	lastName: string = sessionStorage.getItem('lastName');
	email: string = sessionStorage.getItem('email');
	dob: string = moment(parseInt(sessionStorage.getItem('dob'))).format('DD/MM/YYYY');
	gender: string = sessionStorage.getItem('gender');
	id: string = sessionStorage.getItem('id');
	patientRecords: PatientRecords[];
	consultations = 0;


	dtElement: DataTableDirective;
	dtOptions: DataTables.Settings = {};
	dtTrigger: Subject<any> = new Subject();

	ngOnInit(): void {
		this.getPatientRecords();
		this.getAllPatientConsultations();
		this.dtOptions = {
			order: [0, 'asc'],
			pagingType: 'full_numbers',
			pageLength: 10,
		};
	}

	getPatientRecords(): void {
		this.patientService.getPatientRecords(this.id).subscribe(patientRecords => {
			this.patientRecords = patientRecords.data;
			this.dtTrigger.next();
		});
	}

	timeCreated(date): string {
		return moment(date).format('DD/MM/YYYY, h:mm:ss a');
	}

	getAllPatientConsultations(): void {
		this.patientService.getConsultations(this.id).subscribe(consultations => {
			consultations.data.forEach(consultation => {
				if (consultation.seenByPatient == null) {
					this.consultations += 1;
				}
			});
		});
	}
}
