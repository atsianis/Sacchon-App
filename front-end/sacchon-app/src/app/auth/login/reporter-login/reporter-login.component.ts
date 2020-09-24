import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';

@Component({
	selector: 'sacchon-app-reporter-login',
	templateUrl: './reporter-login.component.html',
	styleUrls: ['./reporter-login.component.scss']
})
export class ReporterLoginComponent implements OnInit {

	reporterLoginForm: any;
	constructor(private formBuilder: FormBuilder) {
		this.reporterLoginForm = this.formBuilder.group({
			email: '',
			password: ''
		});
	}

	ngOnInit(): void {
	}

	submitLogin(data: any): any {
		sessionStorage.setItem('email', data.email);
		sessionStorage.setItem('password', data.password);
		if (1) {
			window.location.href = 'http://localhost:4200/reporter';
		} else {
			alert('Invalid username or password');
			this.reporterLoginForm = this.formBuilder.group({
				email: '',
				password: ''
			});
		}
	}
}
