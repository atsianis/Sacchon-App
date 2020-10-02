import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../../auth.service';

@Component({
	selector: 'sacchon-app-patient-sign-up',
	templateUrl: './patient-sign-up.component.html',
	styleUrls: ['./patient-sign-up.component.scss'],
})
export class PatientSignUpComponent implements OnInit {
	constructor(private toastr: ToastrService, private authService: AuthService, private router: Router) { }

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
		if (this.patientSignUp.valid && (this.patientSignUp.get('password').value === this.patientSignUp.get('passwordconfirm').value)) {
			const firstName = this.patientSignUp.get('firstName').value;
			const lastName = this.patientSignUp.get('lastName').value;
			const email = this.patientSignUp.get('email').value;
			const password = this.patientSignUp.get('password').value;
			const unformattedDob = this.patientSignUp.get('dob').value;
			const gender = this.patientSignUp.get('gender').value;

			const dob = new Date(unformattedDob.year, unformattedDob.month - 1, unformattedDob.day);

			this.authService.signUpPatient(firstName, lastName, dob, gender, email, password).subscribe(response => {
				sessionStorage.clear();
				sessionStorage.setItem('email', response.data.email);
				sessionStorage.setItem('password', response.data.password);
				sessionStorage.setItem('firstName', response.data.firstName);
				sessionStorage.setItem('lastName', response.data.lastName);
				sessionStorage.setItem('id', response.data.id);
				sessionStorage.setItem('dob', response.data.dob);
				sessionStorage.setItem('canBeExamined', response.data.canBeExamined);
				sessionStorage.setItem('lastActive', response.data.lastActive);
				sessionStorage.setItem('gender', response.data.gender);
				sessionStorage.setItem('userType', 'patient');
				this.toastr.success('You will be redirected to login page soon.', 'Successfully registered', {
					timeOut: 2000,
					positionClass: 'toast-top-center'
				}).onHidden.toPromise().then(_ => {
					this.router.navigate(['/login/medidatarepo']);
				});
			}, (error) => {
				console.log(error);
			});
		} else {
			this.patientSignUp.markAllAsTouched();
		}
	}
}
