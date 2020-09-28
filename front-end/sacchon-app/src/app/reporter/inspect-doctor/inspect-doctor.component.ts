import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';
import * as moment from 'moment';
import { Doctors } from 'src/app/interfaces/doctors';

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
	constructor(private http: HttpClient) { }

	ngOnInit(): void {
		this.doctors = [];
		this.getDoctors();
		this.dtOptions = {
			pagingType: 'full_numbers',
			pageLength: 5,
			order: [0, 'asc'],
		};
	}

	getDoctors(): void {
		this.http.get<Doctors>('http://localhost:9000/v1/chief/alldoctors').subscribe(doctors => {
			this.doctors = doctors.data;
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
}
