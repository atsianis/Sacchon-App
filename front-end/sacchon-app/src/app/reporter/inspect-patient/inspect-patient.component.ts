import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { DataTableDirective } from 'angular-datatables';
import * as moment from 'moment';
import { ReporterService } from '../reporter.service';

@Component({
	selector: 'sacchon-app-inspect-patient',
	templateUrl: './inspect-patient.component.html',
	styleUrls: ['./inspect-patient.component.scss']
})
export class InspectPatientComponent implements OnInit {

	patients: any;
	dtElement: DataTableDirective;
	dtOptions: DataTables.Settings = {};
	dtTrigger: Subject<any> = new Subject();

	constructor(private reporterService: ReporterService) { }

	ngOnInit(): void {
		this.patients = [];
		this.getPatients();
		this.dtOptions = {
			order: [0, 'asc'],
			pagingType: 'full_numbers',
			pageLength: 10,		};
	}

	getPatients(): void {
		this.reporterService.getPatients().subscribe(patients => {
			this.patients = patients.data;
			this.dtTrigger.next();
		}, (err) => {
			console.log('-----> err', err);
		});
	}

	lastActive(date: number): string {
		if (moment().diff(date, "days") > 0)
			return `${moment().diff(date, "days")} days ago`;
		else
			return `${moment().diff(date, "hours")} hours ago`;
	}

	birthDate(date: number): string {
		return moment(date).format('DD/MM/YYYY');
	}
}
