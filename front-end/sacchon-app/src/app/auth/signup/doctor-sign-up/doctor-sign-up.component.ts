import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../../auth.service';

@Component({
	selector: 'sacchon-app-doctor-sign-up',
	templateUrl: './doctor-sign-up.component.html',
	styleUrls: ['./doctor-sign-up.component.scss'],
})
export class DoctorSignUpComponent implements OnInit {
	constructor(
		private authService: AuthService,
		private toastr: ToastrService,
		private router: Router
	) { }

	doctorSignUp = new FormGroup({
		firstName: new FormControl(null, [Validators.required]),
		lastName: new FormControl(null, [Validators.required]),
		email: new FormControl(null, [Validators.required, Validators.email]),
		password: new FormControl(null, [Validators.required, Validators.minLength(8)]),
		passwordconfirm: new FormControl(null, Validators.required)
	});


	ngOnInit(): void { }

	signUp(): void {
		const firstName = this.doctorSignUp.get('firstName').value;
		const lastName = this.doctorSignUp.get('lastName').value;
		const email = this.doctorSignUp.get('email').value;
		const password = this.doctorSignUp.get('password').value;
		const passwordconfirm = this.doctorSignUp.get('passwordconfirm').value;

		if (this.doctorSignUp.valid && password == passwordconfirm) {
			this.authService.signUpDoctor(firstName, lastName, email, password).subscribe(response => {
				this.toastr.success('You will be redirected to home page soon.', 'Successfully registered', {
					timeOut: 2000,
					positionClass: 'toast-top-center'
				}).onHidden.toPromise().then(_ => {
					this.router.navigate(['/reporter'])
				});
			});
		} else {
			this.doctorSignUp.markAllAsTouched();
		}
	}
}
