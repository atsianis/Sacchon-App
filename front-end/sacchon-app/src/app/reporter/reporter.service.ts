import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class ReporterService {

	constructor(private http: HttpClient) { }

	getDoctors(): Observable<any> {
		return this.http.get<any>('http://localhost:9000/v1/chief/alldoctors');
	}

	getPatients(): Observable<any> {
		return this.http.get<any>('http://localhost:9000/v1/chief/allpatients');
	}

	getCurrentPatientRecords(patient_id): Observable<any> {
		return this.http.get<any>(`http://localhost:9000/v1/patient/${patient_id}/storeData/allData`)
	}

	getPatientById(patient_id): Observable<any> {
		return this.http.get<any>(`http://localhost:9000/v1/patient/${patient_id}`)
	}

	getAllDoctorsFromDatabase(): Observable<any> {
		return this.http.get<any>('http://localhost:9000/v1/chief/alldoctorsDB')
	}

	getAllPatientsFromDatabase(): Observable<any> {
		return this.http.get<any>('http://localhost:9000/v1/chief/allpatientsDB')
	}
}
