import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import * as moment from 'moment';
import { Subject } from 'rxjs';
import { Patients } from 'src/app/interfaces/patients';

@Component({
  selector: 'sacchon-app-all-patients-db',
  templateUrl: './all-patients-db.component.html',
  styleUrls: ['./all-patients-db.component.scss']
})
export class AllPatientsDbComponent implements OnInit {

  patients: any;
	dtElement: DataTableDirective;
	dtOptions: DataTables.Settings = {};
	dtTrigger: Subject<any> = new Subject();

	constructor(private http: HttpClient) { }

	ngOnInit(): void {
		this.patients = [];
		
		this.getPatientsDB();

		this.dtOptions = {
			order: [0, 'asc'],
			pagingType: 'full_numbers',
			pageLength: 5,
		};
	}

	getPatientsDB(): void {
		this.http.get<Patients>('http://localhost:9000/v1/chief/allpatientsDB').subscribe(patients => {
			console.log(patients);	
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
