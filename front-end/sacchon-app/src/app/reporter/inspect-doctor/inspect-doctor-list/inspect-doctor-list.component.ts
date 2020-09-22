import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';

@Component({
	selector: 'sacchon-app-inspect-doctor-list',
	templateUrl: './inspect-doctor-list.component.html',
	styleUrls: ['./inspect-doctor-list.component.scss']
})
export class InspectDoctorListComponent implements OnInit {

	constructor(private route: ActivatedRoute, private http: HttpClient) { }
	dtElement: DataTableDirective;
	dtOptions: DataTables.Settings = {};
	dtTrigger: Subject<any> = new Subject();
	doctor: any;
	consultations: any;

	ngOnInit(): void {
		this.dtOptions = {
			pagingType: 'full_numbers',
			pageLength: 5,
			order: [0, 'asc'],
		};
		this.getDoctorById();
		this.getConsultations();
	}

	getDoctorById(): void {
		this.route.params.subscribe(params => {
			this.http.get(`https://jsonplaceholder.typicode.com/users/${params.id}`).subscribe(doctor => {
				this.doctor = doctor;
			}, (err) => {
				console.log('-----> err', err);
			});
		});
	}

	getConsultations(): void {
		this.route.params.subscribe(params => {
			this.http.get(`https://jsonplaceholder.typicode.com/albums/`).subscribe(consultations => {
				this.consultations = consultations;
				this.dtTrigger.next();
			}, (err) => {
				console.log('-----> err', err);
			});
		});
	}
}
