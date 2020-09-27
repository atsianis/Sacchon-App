import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';

@Component({
	selector: 'sacchon-app-patient',
	templateUrl: './patient.component.html',
	styleUrls: ['./patient.component.scss']
})
export class PatientComponent implements OnInit {

	constructor(private http: HttpClient) { }
	patients: any;
	dtElement: DataTableDirective;
	dtOptions: DataTables.Settings = {};
	dtTrigger: Subject<any> = new Subject();


	ngOnInit(): void {
		this.dtOptions = {
			pagingType: 'full_numbers'
		};
		this.getPatients();
	}

	getPatients(): void {
		this.http.get('http://localhost:9000/v1/patients').subscribe(patients => {
			this.patients = patients;
			this.dtTrigger.next();
		}, (err) => {
			console.log('-----> err', err);
		});
	}
}
