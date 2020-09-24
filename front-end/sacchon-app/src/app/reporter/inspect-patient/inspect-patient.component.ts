import { HttpClient, HttpHeaders } from '@angular/common/http';
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
			order: [0, 'asc'],
			pagingType: 'full_numbers',
			pageLength: 5,
		};
	}

	getPatients(): void {
		const httpOptions = {
			headers: new HttpHeaders({
			  'Authorization': 'Basic ' + btoa('asd@asd.asd:asdasdasd')
			})
		};
		this.http.get('http://localhost:9000/v1/patients', httpOptions).subscribe(patients => {
			this.patients = patients;
			this.dtTrigger.next();
		}, (err) => {
			console.log('-----> err', err);
		});
	}
}
