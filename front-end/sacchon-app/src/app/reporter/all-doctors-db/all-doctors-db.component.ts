import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';
import { Doctors } from 'src/app/interfaces/doctors';

@Component({
  selector: 'sacchon-app-all-doctors-db',
  templateUrl: './all-doctors-db.component.html',
  styleUrls: ['./all-doctors-db.component.scss']
})
export class AllDoctorsDbComponent implements OnInit {

  doctors: any;
  dtElement: DataTableDirective;
	dtOptions: DataTables.Settings = {};
	dtTrigger: Subject<any> = new Subject();
	constructor(private http: HttpClient) { }

	ngOnInit(): void {
		this.doctors = [];
		this.getDoctorsDB();
		this.dtOptions = {
			pagingType: 'full_numbers',
			pageLength: 5,
			order: [0, 'asc'],
		};
	}

	getDoctorsDB(): void {
		this.http.get<Doctors>('http://localhost:9000/v1/chief/alldoctorsDB').subscribe(doctors => {
			this.doctors = doctors.data;
			this.dtTrigger.next();
		}, (err) => {
			console.log('-----> err', err);
		});
	}
}
