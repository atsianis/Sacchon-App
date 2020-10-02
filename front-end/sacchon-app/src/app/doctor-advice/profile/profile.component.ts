import { Component, OnInit } from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';
import { Patients } from 'src/app/interfaces/patients';
import { DoctorAdviceService } from '../doctor-advice.service';

@Component({
	selector: 'sacchon-app-profile',
	templateUrl: './profile.component.html',
	styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

	constructor(private doctorService: DoctorAdviceService) { }
	id: string = sessionStorage.getItem('id');
	firstName: string = sessionStorage.getItem('firstName');
	lastName: string = sessionStorage.getItem('lastName');
	email: string = sessionStorage.getItem('email');
	patients: Patients[];
	availablePatients: number;
	consultablePatients: number;

	dtElement: DataTableDirective;
	dtOptions: DataTables.Settings = {};
	dtTrigger: Subject<any> = new Subject();

	ngOnInit(): void {
		this.getNotifications();
		this.getPatients();
		this.dtOptions = {
			order: [0, 'asc'],
			pagingType: 'full_numbers',
			pageLength: 10,		};
	}

	getPatients(): void {
		this.doctorService.getAllDoctorsPatients(this.id).subscribe(patients => {
			this.patients = patients.data;
			this.dtTrigger.next();
		}, (err) => {
			console.log('-----> err', err);
		});
	}

	getNotifications(): void {
		this.doctorService.getAvailablePatients(this.id).subscribe(patients => {
			this.availablePatients = patients.data.length;
		});
		this.doctorService.getConsultablePatients(this.id).subscribe(patients => {
			this.consultablePatients = patients.data.length;
		});
	}

	lastActive(date: number): string {
		return this.doctorService.lastActive(date);
	}

	birthDate(date: number): string {
		return this.doctorService.birthDate(date);
	}
}
