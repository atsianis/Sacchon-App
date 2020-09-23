import { Component, OnInit } from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';
import { HttpClient } from '@angular/common/http';


@Component({
	selector: 'sacchon-app-profile',
	templateUrl: './profile.component.html',
	styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

	constructor(private http: HttpClient) { }

	patients: any;
	dtElement: DataTableDirective;
	dtOptions: DataTables.Settings = {};
	dtTrigger: Subject<any> = new Subject();

	getPatient(patient: any): void {
		window.location.href = `/reporter/patient/${patient[0]}`;
	}

	ngOnInit(): void {
		this.patients = [];
		this.getPatients();
		this.dtOptions = {
			order: [0, 'asc'],
			pagingType: 'full_numbers',
			pageLength: 5,
		};
	}
	getPatients(): void {
		this.http.get('https://jsonplaceholder.typicode.com/users').subscribe(patients => {
			this.patients = patients;
			this.dtTrigger.next();
		}, (err) => {
			console.log('-----> err', err);
		});
	}
}
