import { Component, OnInit } from '@angular/core';
import {
	FormControl,
	FormGroup,
	ValidationErrors,
	Validators,
} from '@angular/forms';

@Component({
	selector: 'sacchon-app-doctor-sign-up',
	templateUrl: './doctor-sign-up.component.html',
	styleUrls: ['./doctor-sign-up.component.scss'],
})
export class DoctorSignUpComponent implements OnInit {
	constructor() {}

	doctorSignUp = new FormGroup({
		firstName: new FormControl(null, [Validators.required]),
		lastName: new FormControl(null, [Validators.required]),
		email: new FormControl(null, [Validators.required]),
		password: new FormControl(null, [Validators.required]),
		passwordconfirm: new FormControl(null, [Validators.required]),
	});

	ngOnInit(): void {}

	signUp() {
		if (this.doctorSignUp.valid) {
			if (
				this.doctorSignUp.get('password').value ===
				this.doctorSignUp.get('passwordconfirm').value
			) {
				console.log(this.doctorSignUp.get('password').value);
				console.log('EO', this.doctorSignUp.value);
			} else {
				console.log('pouli');
			}
		} else {
			Object.keys(this.doctorSignUp.controls).forEach((key) => {
				const controlErrors: ValidationErrors = this.doctorSignUp.get(
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
