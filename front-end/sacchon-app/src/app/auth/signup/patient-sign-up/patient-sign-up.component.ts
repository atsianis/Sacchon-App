import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
	selector: 'sacchon-app-patient-sign-up',
	templateUrl: './patient-sign-up.component.html',
	styleUrls: ['./patient-sign-up.component.scss'],
})
export class PatientSignUpComponent implements OnInit {
	constructor(private toastr: ToastrService) { }

	patientSignUp = new FormGroup({
		firstName: new FormControl(null, [Validators.required]),
		lastName: new FormControl(null, [Validators.required]),
		email: new FormControl(null, [Validators.required, Validators.email]),
		password: new FormControl(null, [Validators.required, Validators.minLength(8)]),
		passwordconfirm: new FormControl(null, [Validators.required]),
	});

	ngOnInit(): void { }

	signUp(): void {
		if (this.patientSignUp.valid && (this.patientSignUp.get('password').value === this.patientSignUp.get('passwordconfirm').value)) {
			console.log(this.patientSignUp.get('password').value);
			console.log('EO', this.patientSignUp.value);
			this.toastr.success('You will be redirected to home page soon.', 'Successfully registered', {
				timeOut: 2000,
				positionClass: 'toast-top-center'
			}).onHidden.toPromise().then(_ => {
				location.href = '/doctoradvice/profile';
			});
		} else {
			this.patientSignUp.markAllAsTouched();
		}
	}
}
