import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';

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
			order: [1, 'desc']
		};
	}

	getDoctors(): void {
		this.http.get('https://jsonplaceholder.typicode.com/users').subscribe(doctors => {
			this.doctors = doctors;
			this.dtTrigger.next();
		}, (err) => {
			console.log('-----> err', err);
		});
	}
}
