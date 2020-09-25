import { Component, OnInit } from '@angular/core';
import {
	FormControl,
	FormGroup,
	ValidationErrors,
	Validators,
} from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
	selector: 'sacchon-app-doctor-sign-up',
	templateUrl: './doctor-sign-up.component.html',
	styleUrls: ['./doctor-sign-up.component.scss'],
})
export class DoctorSignUpComponent implements OnInit {
	constructor(private toastr: ToastrService) { }

	doctorSignUp = new FormGroup({
		firstName: new FormControl(null, [Validators.required]),
		lastName: new FormControl(null, [Validators.required]),
		email: new FormControl(null, [Validators.required, Validators.email]),
		password: new FormControl(null, [Validators.required, Validators.minLength(8)]),
		passwordconfirm: new FormControl(null)
	});

	ngOnInit(): void { }

	signUp(): void {
		if (this.doctorSignUp.valid) {
			if (this.doctorSignUp.get('password').value === this.doctorSignUp.get('passwordconfirm').value) {
				console.log(this.doctorSignUp.get('password').value);
				console.log('EO', this.doctorSignUp.value);
				this.toastr.success('You will be redirected to home page soon.', 'Successfully registered', {
					timeOut: 2000,
					positionClass: 'toast-top-center'
				}).onHidden.toPromise().then(_ => {
					location.href = '/doctoradvice/profile';
				});
			} else {
				this.toastr.error('Passwords do not match.')
			}
		} else {
			Object.keys(this.doctorSignUp.controls).forEach((key) => {
				const controlErrors: ValidationErrors = this.doctorSignUp.get(key).errors;
				if (controlErrors != null) {
					Object.keys(controlErrors).forEach((keyError) => {
						this.toastr.error(`The field ${key} is ${keyError}`);
					});
				}
			});
		}
	}
}
