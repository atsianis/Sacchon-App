import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Chiefs } from 'src/app/interfaces/chiefs';

@Component({
	selector: 'sacchon-app-reporter-login',
	templateUrl: './reporter-login.component.html',
	styleUrls: ['./reporter-login.component.scss']
})
export class ReporterLoginComponent implements OnInit {

	constructor(private formBuilder: FormBuilder, private http: HttpClient) {	}

	reporterLoginForm: any = this.formBuilder.group({
		email: '',
		password: ''
	});

	ngOnInit(): void {
	}

	httpOptions = {
		headers: new HttpHeaders({
			'Authorization': 'Basic ' + btoa('asd@asd.asd:asdasdasd'),
			'Access-Control-Allow-Origin': '*'
		})
	};

	submitLogin(): any {
		this.http.post<Chiefs>('http://localhost:9000/v1/login/chief', {
			email: this.reporterLoginForm.get('email').value,
			password: this.reporterLoginForm.get('password').value
		}, this.httpOptions).subscribe(response => {
			console.log(response);
			if (response) {
				sessionStorage.setItem('email', response?.email );
				sessionStorage.setItem('password', response?.password );
				sessionStorage.setItem('firstName', response?.firstName );
				sessionStorage.setItem('lastName', response?.lastName );
				sessionStorage.setItem('id', response?.id );
				window.location.href = 'http://localhost:4200/reporter';
			} else {
				alert('Invalid username or password');
				this.reporterLoginForm = this.formBuilder.group({
					email: '',
					password: ''
				});
			}
		})
	}
}
