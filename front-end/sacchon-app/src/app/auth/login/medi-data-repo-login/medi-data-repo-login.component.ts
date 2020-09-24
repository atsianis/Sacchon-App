import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';

@Component({
	selector: 'sacchon-app-medi-data-repo-login',
	templateUrl: './medi-data-repo-login.component.html',
	styleUrls: ['./medi-data-repo-login.component.scss']
})
export class MediDataRepoLoginComponent implements OnInit {

	patientLoginForm: any;
	constructor(private formBuilder: FormBuilder) {
		this.patientLoginForm = this.formBuilder.group({
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
			window.location.href = 'http://localhost:4200/medidatarepo/profile';
		} else {
			alert('Invalid username or password');
			this.patientLoginForm = this.formBuilder.group({
				email: '',
				password: ''
			});
		}
	}
}
