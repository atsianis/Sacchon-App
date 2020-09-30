import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import * as moment from 'moment';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class DoctorAdviceService {

	constructor(private http: HttpClient) { }

	lastActive(date: number): string {
		if (moment().diff(date, "days") > 0)
			return `${moment().diff(date, "days")} days ago`;
		else
			return `${moment().diff(date, "hours")} hours ago`;
	}

	birthDate(date: number): string {
		return moment(date).format('DD/MM/YYYY');
	}

	getCurrentPatientRecords(patient_id): Observable<any> {
		return this.http.get<any>(`http://localhost:9000/v1/patient/${patient_id}/storeData/allData`)
	}

	getPatientById(patient_id): Observable<any> {
		return this.http.get<any>(`http://localhost:9000/v1/patient/${patient_id}`)
	}

	getAllDoctorsPatients(doctor_id): Observable<any> {
		return this.http.get<any>(`http://localhost:9000/v1/doctor/${doctor_id}/mypatients`)
	}

	getDoctorById(doctor_id): Observable<any> {
		return this.http.get<any>(`http://localhost:9000/v1/doctor/${doctor_id}`)
	}

	getCurrentDoctorConsultations(doctor_id): Observable<any> {
		return this.http.get<any>(`http://localhost:9000/v1/doctor/${doctor_id}/consultations`)
	}

	getInactiveDoctors(): Observable<any> {
		return this.http.get<any>('http://localhost:9000/v1/chief/inactivedoctors')
	}

	getInactivePatients(): Observable<any> {
		return this.http.get<any>('http://localhost:9000/v1/chief/inactivepatients')
	}

	getPatientConsultations(patient_id): Observable<any> {
		return this.http.get<any>(`http://localhost:9000/v1/patient/${patient_id}/consultations`)
	}

	getConsultationById(consultation_id): Observable<any> {
		return this.http.get<any>(`http://localhost:9000/v1/consultation/${consultation_id}`)
	}

	editConsultation(consultation_id, doctor_id, comment): Observable<any> {
		return this.http.put(`http://localhost:9000/v1/consultation/${consultation_id}/doctor/${doctor_id}/`, {
			comment: comment
		})
	}

	getAvailablePatients(doctor_id): Observable<any>{
		return this.http.get<any>(`http://localhost:9000/v1/doctor/${doctor_id}/available-patients`)
	}
}
