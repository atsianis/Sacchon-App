import { Component, OnInit } from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';
import * as moment from 'moment';
import { ReporterService } from '../reporter.service';

@Component({
	selector: 'sacchon-app-inspect-doctor',
	templateUrl: './inspect-doctor.component.html',
	styleUrls: ['./inspect-doctor.component.scss']
})
export class InspectDoctorComponent implements OnInit {

	doctors: any;
	dtElement: DataTableDirective;
	dtOptions: DataTables.Settings = {};
	dtTrigger: Subject<any> = new Subject();
	constructor(private reporterService: ReporterService) { }

	ngOnInit(): void {
		this.doctors = [];
		this.getDoctors();
		this.dtOptions = {
			pagingType: 'full_numbers',
			pageLength: 10,			order: [0, 'asc'],
		};
	}

	getDoctors(): void {
		this.reporterService.getDoctors().subscribe(doctors => {
			this.doctors = doctors.data;
			this.dtTrigger.next();
		}, (err) => {
			console.log('-----> err', err);
		});
	}

	lastActive(date: number): string {
		if (moment().diff(date, 'days') > 0) {
			return `${moment().diff(date, 'days')} days ago`;
		}
		else {
			return `${moment().diff(date, 'hours')} hours ago`;
		}
	}
}
