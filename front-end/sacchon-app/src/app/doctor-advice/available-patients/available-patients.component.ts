import { Component, OnInit } from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import * as moment from 'moment';
import { Subject } from 'rxjs';
import { DoctorAdviceService } from '../doctor-advice.service';

@Component({
	selector: 'sacchon-app-available-patients',
	templateUrl: './available-patients.component.html',
	styleUrls: ['./available-patients.component.scss']
})
export class AvailablePatientsComponent implements OnInit {

	patients: any;
	dtElement: DataTableDirective;
	dtOptions: DataTables.Settings = {};
	dtTrigger: Subject<any> = new Subject();

	constructor(private doctorservice: DoctorAdviceService) { }

	id: string = sessionStorage.getItem('id');

	ngOnInit(): void {
		this.patients = [];
		this.getAvailablePatients();
		this.dtOptions = {
			order: [0, 'asc'],
			pagingType: 'full_numbers',
			pageLength: 5,
		};
	}

	getAvailablePatients(): void {
		this.doctorservice.getAvailablePatients(this.id).subscribe(patients => {
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
