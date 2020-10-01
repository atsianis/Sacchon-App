import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
	selector: 'sacchon-app-patient-sign-up',
	templateUrl: './patient-sign-up.component.html',
	styleUrls: ['./patient-sign-up.component.scss'],
})
export class PatientSignUpComponent implements OnInit {
	constructor(private toastr: ToastrService, private http: HttpClient) { }

	patientSignUp = new FormGroup({
		firstName: new FormControl(null, [Validators.required]),
		lastName: new FormControl(null, [Validators.required]),
		email: new FormControl(null, [Validators.required, Validators.email]),
		password: new FormControl(null, [Validators.required, Validators.minLength(8)]),
		passwordconfirm: new FormControl(null, [Validators.required]),
		dob: new FormControl(null, [Validators.required]),
		gender: new FormControl(null, [Validators.required]),
	});

	selectGender: string[] = ['male', 'female'];

	ngOnInit(): void { }

	signUp(): void {
		if (this.patientSignUp.valid && (this.patientSignUp.get('password').value === this.patientSignUp.get('passwordconfirm').value)) {
			this.http.post(`http://localhost:9000/v1/register/patient`, {
				firstName: this.patientSignUp.get('firstName').value,
				lastName: this.patientSignUp.get('lastName').value,
				email: this.patientSignUp.get('email').value,
				password: this.patientSignUp.get('password').value,
				dob: "1994-01-10", //WIP: add datepicker
				gender: "male" //WIP: add gender selector
			}).subscribe(response => {
				this.toastr.success('You will be redirected to home page soon.', 'Successfully registered', {
					timeOut: 2000,
					positionClass: 'toast-top-center'
				}).onHidden.toPromise().then(_ => {
					location.href = '/doctoradvice/profile';
				});
			}, (error) => {
				console.log(error)
			})
		} else {
			this.patientSignUp.markAllAsTouched();
		}
	}
}
