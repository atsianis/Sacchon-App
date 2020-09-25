import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';

@Component({
	selector: 'sacchon-app-patient-sign-up',
	templateUrl: './patient-sign-up.component.html',
	styleUrls: ['./patient-sign-up.component.scss'],
})
export class PatientSignUpComponent implements OnInit {
	constructor() {}

	patientSignUp = new FormGroup({
		firstName: new FormControl(null, [Validators.required]),
		lastName: new FormControl(null, [Validators.required]),
		email: new FormControl(null, [Validators.required]),
		password: new FormControl(null, [Validators.required]),
		passwordconfirm: new FormControl(null, [Validators.required]),
	});

	ngOnInit(): void {}
	signUp() {
		if (this.patientSignUp.valid) {
			if (
				this.patientSignUp.get('password').value ===
				this.patientSignUp.get('passwordconfirm').value
			) {
				console.log(this.patientSignUp.get('password').value);
				console.log('EO', this.patientSignUp.value);
			} else {
				console.log('pouli');
			}
		} else {
			Object.keys(this.patientSignUp.controls).forEach((key) => {
				const controlErrors: ValidationErrors = this.patientSignUp.get(
					key
				).errors;
				if (controlErrors != null) {
					Object.keys(controlErrors).forEach((keyError) => {
						console.log(
							'Key control: ' +
								key +
								', keyError: ' +
								keyError +
								', err value: ',
							controlErrors[keyError]
						);
					});
				}
			});
		}
	}
}
