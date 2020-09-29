import { Component, OnInit } from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Patients } from 'src/app/interfaces/patients';
import { DoctorAdviceService } from '../doctor-advice.service';

@Component({
	selector: 'sacchon-app-profile',
	templateUrl: './profile.component.html',
	styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

	constructor(private http: HttpClient, private doctorService: DoctorAdviceService) { }
	id: string;
	firstName: string;
	lastName: string;
	email: string;
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
		this.id = sessionStorage.getItem('id');
		this.firstName = sessionStorage.getItem('firstName');
		this.lastName = sessionStorage.getItem('lastName');
		this.email = sessionStorage.getItem('email');
		this.http.get<Patients>(`http://localhost:9000/v1/doctor/${this.id}/mypatients`).subscribe(patients => {
			this.patients = patients.data;
			this.dtTrigger.next();
		}, (err) => {
			console.log('-----> err', err);
		});
	}

	lastActive(date: number): string {
		return this.doctorService.lastActive(date)
	}

	birthDate(date: number): string {
		return this.doctorService.birthDate(date)
	}
}
