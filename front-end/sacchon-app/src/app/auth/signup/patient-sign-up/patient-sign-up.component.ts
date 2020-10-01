import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
	selector: 'sacchon-app-patient-sign-up',
	templateUrl: './patient-sign-up.component.html',
	styleUrls: ['./patient-sign-up.component.scss'],
})
export class PatientSignUpComponent implements OnInit {
	constructor(private toastr: ToastrService, private http: HttpClient, private router: Router) { }

	patientSignUp = new FormGroup({
		firstName: new FormControl(null, [Validators.required]),
		lastName: new FormControl(null, [Validators.required]),
		email: new FormControl(null, [Validators.required, Validators.email]),
		password: new FormControl(null, [Validators.required, Validators.minLength(8)]),
		passwordconfirm: new FormControl(null, [Validators.required]),
		dob: new FormControl(null, [Validators.required]),
		gender: new FormControl(null, [Validators.required]),
	});

	selectGender: string[] = ['Male', 'Female'];

	ngOnInit(): void { }

	signUp(): void {
		if (this.patientSignUp.get('password').value === this.patientSignUp.get('passwordconfirm').value) {
			this.http.post(`http://localhost:9000/v1/register/patient`, {
				firstName: this.patientSignUp.get('firstName').value,
				lastName: this.patientSignUp.get('lastName').value,
				email: this.patientSignUp.get('email').value,
				password: this.patientSignUp.get('password').value,
				dob: "1994-01-10", //WIP: add datepicker
				gender: this.patientSignUp.get('gender').value
			}).subscribe(response => {
				this.toastr.success('You will be redirected to login page soon.', 'Successfully registered', {
					timeOut: 2000,
					positionClass: 'toast-top-center'
				}).onHidden.toPromise().then(_ => {
					this.router.navigate(['/login/medidatarepo'])
				}, error => {
					console.log(error)
					// this.toastr.error(response)
				});
			}, (error) => {
				console.log(error)
			})
		} else {
			this.patientSignUp.markAllAsTouched();
		}
	}
}
