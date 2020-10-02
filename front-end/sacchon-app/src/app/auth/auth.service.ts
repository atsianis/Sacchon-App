import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class AuthService {

	constructor(private http: HttpClient) { }

	isLoggedIn(): boolean {
		if (sessionStorage.getItem('userType')) {
			return true;
		}

		return false;
	}

	doctorLogin(email, password): Observable<any> {
		return this.http.post<any>('http://localhost:9000/v1/login/doctor', {
			email,
			password
		});
	}

	patientLogin(email, password): Observable<any> {
		return this.http.post<any>('http://localhost:9000/v1/login/patient', {
			email,
			password
		});
	}

	reporterLogin(email, password): Observable<any> {
		return this.http.post<any>('http://localhost:9000/v1/login/chief', {
			email,
			password
		});
	}

	signUpDoctor(firstName, lastName, email, password): Observable<any> {
		return this.http.post(`http://localhost:9000/v1/chief/register/doctor`, {
				firstName,
				lastName,
				email,
				password,
		});
	}

	signUpPatient(firstName, lastName, dob, gender, email, password): Observable<any> {
		return this.http.post(`http://localhost:9000/v1/register/patient`, {
				firstName,
				lastName,
				email,
				password,
				dob,
				gender
		});
	}
}
