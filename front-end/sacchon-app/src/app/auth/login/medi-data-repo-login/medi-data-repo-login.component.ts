import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Patients } from 'src/app/interfaces/patients';

@Component({
	selector: 'sacchon-app-medi-data-repo-login',
	templateUrl: './medi-data-repo-login.component.html',
	styleUrls: ['./medi-data-repo-login.component.scss']
})
export class MediDataRepoLoginComponent implements OnInit {

	constructor(private http: HttpClient, private router: Router, private toastr: ToastrService) {	}

	patientLoginForm = new FormGroup({
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
		this.http.post<Patients>('http://localhost:9000/v1/login/patient', {
			email: this.patientLoginForm.get('email').value,
			password: this.patientLoginForm.get('password').value
		}, this.httpOptions).subscribe(response => {
			if (response) {
				this.toastr.success(`Welcome ${response.firstName}!`, 'Login successful', {
					timeOut: 2000,
					positionClass: 'toast-top-center'
				}).onHidden.toPromise().then(_ => {
					console.log(response);
					sessionStorage.setItem('email', response.email);
					sessionStorage.setItem('password', response.password);
					sessionStorage.setItem('firstName', response.firstName);
					sessionStorage.setItem('lastName', response.lastName);
					sessionStorage.setItem('id', response.id);
					sessionStorage.setItem('dob', response.dob);
					sessionStorage.setItem('canBeExamined', response.canBeExamined);
					sessionStorage.setItem('lastActive', response.lastActive);
					sessionStorage.setItem('gender', response.gender);
					this.router.navigate(['medidatarepo/profile']);
				})
			} else {
				this.toastr.error('Invalid credentials', 'Login Unsuccessful')
			}
		})
	}
}
