import { Component, OnInit } from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';
import { DoctorAdviceService } from '../doctor-advice.service';
import * as moment from 'moment'

@Component({
	selector: 'sacchon-app-consultable-patients',
	templateUrl: './consultable-patients.component.html',
	styleUrls: ['./consultable-patients.component.scss']
})
export class ConsultablePatientsComponent implements OnInit {

	patients: any;
	dtElement: DataTableDirective;
	dtOptions: DataTables.Settings = {};
	dtTrigger: Subject<any> = new Subject();

	constructor(private doctorservice: DoctorAdviceService) { }

	id: string = sessionStorage.getItem('id');

	ngOnInit(): void {
		this.patients = [];
		this.consultablePatients();
		this.dtOptions = {
			order: [0, 'asc'],
			pagingType: 'full_numbers',
			pageLength: 10,
		};
	}

	consultablePatients(): void {
		this.doctorservice.getConsultablePatients(this.id).subscribe(patients => {
			this.patients = patients.data;
			this.dtTrigger.next();
		}, (err) => {
			console.log('-----> err', err);
		});
	}

	birthDate(date: number): string {
		return moment(date).format('DD/MM/YYYY');
	}

}
