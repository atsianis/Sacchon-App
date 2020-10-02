import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class MediDataRepoService {

	constructor(private http: HttpClient) { }

	submitPatientRecord(patient_id, glycose, carbs): Observable<any> {
		return this.http.post(`http://localhost:9000/v1/patient/${patient_id}/addpatientrecord`, {
			glycose,
			carbs
		});
	}

	getPatientRecords(patient_id): Observable<any> {
		return this.http.get<any>(`http://localhost:9000/v1/patient/${patient_id}/allpatientrecords`);
	}

	getPatientRecordById(patient_id, record_id): Observable<any> {
		return this.http.get<any>(`http://localhost:9000/v1/patient/${patient_id}/patientRecord/${record_id}`);
	}

	editPatientRecord(patient_id, record_id, glycose, carbs): Observable<any> {
		return this.http.put<any>(`http://localhost:9000/v1/patient/${patient_id}/patientRecord/${record_id}`, {
			glycose,
			carbs
		});
	}

	getConsultations(patient_id): Observable<any> {
		return this.http.get<any>(`http://localhost:9000/v1/patient/${patient_id}/consultations`);
	}

	editProfile(patient_id, firstName, lastName, email, dob, password): Observable<any> {
		return this.http.put<any>(`http://localhost:9000/v1/patient/${patient_id}/settings`, {
			firstName,
			lastName,
			email,
			dob,
			password,
		});
	}

	softDelete(patient_id): Observable<any> {
		return this.http.put(`http://localhost:9000/v1/patient/${patient_id}/settings/softDelete`, null);
	}

	markConsultationAsRead(patient_id, consultation_id): Observable<any> {
		return this.http.put(`http://localhost:9000/v1/patient/${patient_id}/consultation/${consultation_id}/read`, null);
	}
}
