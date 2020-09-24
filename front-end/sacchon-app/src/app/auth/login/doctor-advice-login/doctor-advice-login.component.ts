import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';

@Component({
	selector: 'sacchon-app-doctor-advice-login',
	templateUrl: './doctor-advice-login.component.html',
	styleUrls: ['./doctor-advice-login.component.scss']
})
export class DoctorAdviceLoginComponent implements OnInit {

	doctorLoginForm: any;
	constructor(private formBuilder: FormBuilder) {
		this.doctorLoginForm = this.formBuilder.group({
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
			window.location.href = 'http://localhost:4200/doctoradvice/profile';
		} else {
			alert('Invalid username or password');
			this.doctorLoginForm = this.formBuilder.group({
				email: '',
				password: ''
			});
		}
	}
}
