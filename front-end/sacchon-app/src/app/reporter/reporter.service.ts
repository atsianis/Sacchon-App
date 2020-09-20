import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Doctors } from '../interfaces/doctors';
import { Patients } from '../interfaces/patients';

@Injectable({
	providedIn: 'root'
})
export class ReporterService {

	constructor(private http: HttpClient) { }

	getDoctors(): Observable<Doctors[]> {
		return this.http.get<Doctors[]>('https://swapi.dev/api/people');
	}

	getPatients(): Observable<Patients[]> {
		return this.http.get<Patients[]>('https://swapi.dev/api/people');
	}
}
