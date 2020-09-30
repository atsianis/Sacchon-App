import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class ReporterService {

	constructor(private http: HttpClient) { }

	// All users Services
	getDoctors(): Observable<any> {
		return this.http.get<any>('http://localhost:9000/v1/chief/alldoctors');
	}

	getPatients(): Observable<any> {
		return this.http.get<any>('http://localhost:9000/v1/chief/allpatients');
	}

	// Patient Services
	getCurrentPatientRecords(patient_id): Observable<any> {
		return this.http.get<any>(`http://localhost:9000/v1/patient/${patient_id}/allpatientrecords`)
	}

	getPatientById(patient_id): Observable<any> {
		return this.http.get<any>(`http://localhost:9000/v1/patient/${patient_id}`)
	}

	// Doctor Services
	getDoctorById(doctor_id): Observable<any> {
		return this.http.get<any>(`http://localhost:9000/v1/doctor/${doctor_id}`)
	}

	getCurrentDoctorConsultations(doctor_id): Observable<any> {
		return this.http.get<any>(`http://localhost:9000/v1/doctor/${doctor_id}/consultations`)
	}


	// Entire Database Services
	getAllDoctorsFromDatabase(): Observable<any> {
		return this.http.get<any>('http://localhost:9000/v1/chief/alldoctorsDB')
	}

	getAllPatientsFromDatabase(): Observable<any> {
		return this.http.get<any>('http://localhost:9000/v1/chief/allpatientsDB')
	}

	// Inactive Services
	getInactiveDoctors(): Observable<any> {
		return this.http.get<any>('http://localhost:9000/v1/chief/inactivedoctors')
	}

	getInactivePatients(): Observable<any> {
		return this.http.get<any>('http://localhost:9000/v1/chief/inactivepatients')
	}
}
