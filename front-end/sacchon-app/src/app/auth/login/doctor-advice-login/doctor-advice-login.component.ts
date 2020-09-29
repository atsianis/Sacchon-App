import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { report } from 'process';
import { Doctors } from 'src/app/interfaces/doctors';

@Component({
	selector: 'sacchon-app-doctor-advice-login',
	templateUrl: './doctor-advice-login.component.html',
	styleUrls: ['./doctor-advice-login.component.scss']
})
export class DoctorAdviceLoginComponent implements OnInit {

	constructor(private http: HttpClient, private router: Router, private toastr: ToastrService) {	}

	doctorLoginForm = new FormGroup({
		email: new FormControl(null, [Validators.required, Validators.email]),
		password: new FormControl(null, [Validators.required, Validators.minLength(8)]),
	});

	doctor: any;

	ngOnInit(): void {
	}

	submitLogin(): any {
		this.http.post<Doctors>('http://localhost:9000/v1/login/doctor', {
			email: this.doctorLoginForm.get('email').value,
			password: this.doctorLoginForm.get('password').value
		}).subscribe(response => {
			if (response.status == 200) {
				this.toastr.success(`Welcome ${response.data[0].firstName}!`, 'Login successful', {
					timeOut: 2000,
					positionClass: 'toast-top-center'
				}).onHidden.toPromise().then(_ => {
					sessionStorage.setItem('email', response.data[0].email);
					sessionStorage.setItem('password', response.data[0].password);
					sessionStorage.setItem('firstName', response.data[0].firstName);
					sessionStorage.setItem('lastName', response.data[0].lastName);
					sessionStorage.setItem('id', response.data[0].id);
					sessionStorage.setItem('lastActive', response.data[0].lastActive);
					sessionStorage.setItem('userType', 'doctor');
					this.router.navigate(['doctoradvice/profile']);
				})
			} else {
				this.toastr.error('Invalid credentials', 'Login Unsuccessful', {
					timeOut: 2000,
					positionClass: 'toast-top-center'
				})
			}
		})
	}
}
