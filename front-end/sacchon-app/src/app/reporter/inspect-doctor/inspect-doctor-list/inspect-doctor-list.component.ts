import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
	selector: 'sacchon-app-inspect-doctor-list',
	templateUrl: './inspect-doctor-list.component.html',
	styleUrls: ['./inspect-doctor-list.component.scss']
})
export class InspectDoctorListComponent implements OnInit {

	constructor(private route: ActivatedRoute, private http: HttpClient) { }
	dtOptions: DataTables.Settings = {};
	doctor: any;

	ngOnInit(): void {
		this.dtOptions = {
			pagingType: 'full_numbers'
		};
		this.getDoctorById();
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

}
