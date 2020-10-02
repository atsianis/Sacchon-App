import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

@Injectable({
	providedIn: 'root'
})
export class ReporterService {

	constructor(private http: HttpClient) { }

	// All users Services
	getDoctors(): Observable<any> {
		return this.http.get<any>('http://localhost:9000/v1/chief/doctors');
	}

	getPatients(): Observable<any> {
		return this.http.get<any>('http://localhost:9000/v1/chief/patients');
	}

	// Patient Services
	getCurrentPatientRecords(patient_id): Observable<any> {
		return this.http.get<any>(`http://localhost:9000/v1/patient/${patient_id}/allpatientrecords`);
	}

	getPatientById(patient_id): Observable<any> {
		return this.http.get<any>(`http://localhost:9000/v1/patient/${patient_id}`);
	}

	// Doctor Services
	getDoctorById(doctor_id): Observable<any> {
		return this.http.get<any>(`http://localhost:9000/v1/doctor/${doctor_id}`);
	}

	getCurrentDoctorConsultations(doctor_id): Observable<any> {
		return this.http.get<any>(`http://localhost:9000/v1/doctor/${doctor_id}/consultations`);
	}


	// Entire Database Services
	getAllDoctorsFromDatabase(): Observable<any> {
		return this.http.get<any>('http://localhost:9000/v1/chief/alldoctors');
	}

	getAllPatientsFromDatabase(): Observable<any> {
		return this.http.get<any>('http://localhost:9000/v1/chief/allpatients');
	}

	// Inactive Services
	getInactiveDoctors(): Observable<any> {
		return this.http.get<any>('http://localhost:9000/v1/chief/inactivedoctors');
	}

	getInactivePatients(): Observable<any> {
		return this.http.get<any>('http://localhost:9000/v1/chief/inactivepatients');
	}

	lastActive(date: number): string {
		if (moment().diff(date, 'days') > 0) {
			return `${moment().diff(date, 'days')} days ago`;
		}
		else {
			return `${moment().diff(date, 'hours')} hours ago`;
		}
	}

	getConsultablePatients(): Observable<any> {
		return this.http.get<any>('http://localhost:9000/v1/chief/consultablepatients');
	}

	editProfile(firstName, lastName, email, password): Observable<any> {
		return this.http.put(`http://localhost:9000/v1/chief/settings/update`, {
			firstName,
			lastName,
			email,
			password
		});
	}
}
