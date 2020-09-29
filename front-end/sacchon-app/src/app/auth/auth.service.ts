import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class AuthService {

	constructor(private http: HttpClient) { }

	isLoggedIn(): boolean {
		if (sessionStorage.getItem('userType'))
			return true

		return false
	}

	doctorLogin(email, password): Observable<any> {
		return this.http.post<any>('http://localhost:9000/v1/login/doctor', {
			email: email,
			password: password
		})
	}
	
	patientLogin(email, password): Observable<any> {
		return this.http.post<any>('http://localhost:9000/v1/login/patient', {
			email: email,
			password: password
		})
	}

	reporterLogin(email, password): Observable<any> {
		return this.http.post<any>('http://localhost:9000/v1/login/chief', {
			email: email,
			password: password
		})
	}

	signUpDoctor(firstName, lastName, email, password): Observable<any> {
		return this.http.post(`http://localhost:9000/v1/create/doctor`, {
				firstName: firstName,
				lastName: lastName,
				email: email,
				password: password,
		})
	}
}
