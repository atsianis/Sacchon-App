import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
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

	httpOptions = {
		headers: new HttpHeaders({
			'Authorization': 'Basic ' + btoa('asd@asd.asd:asdasdasd'),
			'Access-Control-Allow-Origin': '*'
		})
	};

	ngOnInit(): void {
	}

	submitLogin(): any {
		this.http.post<Doctors>('http://localhost:9000/v1/login/doctor', {
			email: this.doctorLoginForm.get('email').value,
			password: this.doctorLoginForm.get('password').value
		}, this.httpOptions).subscribe(response => {
			if (response.status == 200) {
				this.toastr.success(`Welcome ${response.firstName}!`, 'Login successful', {
					timeOut: 2000,
					positionClass: 'toast-top-center'
				}).onHidden.toPromise().then(_ => {
					sessionStorage.setItem('email', response.data.email);
					sessionStorage.setItem('password', response.data.password);
					sessionStorage.setItem('firstName', response.data.firstName);
					sessionStorage.setItem('lastName', response.data.lastName);
					sessionStorage.setItem('id', response.data.id);
					sessionStorage.setItem('lastActive', response.data.lastActive);
					this.router.navigate(['doctoradvice/profile']);
				})
			} else {
				this.toastr.error('Invalid credentials', 'Login Unsuccessful')
			}
		})
	}
}
