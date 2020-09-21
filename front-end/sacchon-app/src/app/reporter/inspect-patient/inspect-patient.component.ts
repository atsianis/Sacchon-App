import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { DataTableDirective } from 'angular-datatables';

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

	constructor(private http: HttpClient) { }

	ngOnInit(): void {
		this.patients = [];
		this.getPatients();
		this.dtOptions = {
			order: [1, 'desc'],
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
