import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../../auth.service';

@Component({
	selector: 'sacchon-app-doctor-advice-login',
	templateUrl: './doctor-advice-login.component.html',
	styleUrls: ['./doctor-advice-login.component.scss']
})
export class DoctorAdviceLoginComponent implements OnInit {

	constructor(private router: Router, private toastr: ToastrService, private authService: AuthService) { }

	doctorLoginForm = new FormGroup({
		email: new FormControl(null, [Validators.required, Validators.email]),
		password: new FormControl(null, [Validators.required, Validators.minLength(8)]),
	});

	ngOnInit(): void {
	}

	submitLogin(): void {
		const email = this.doctorLoginForm.get('email').value;
		const password = this.doctorLoginForm.get('password').value;

		this.authService.doctorLogin(email, password).subscribe(response => {
			this.handleLogin(response);
		});
	}

	handleLogin(response): void {
		if (response.status == 200) {
			this.toastr.success(`Welcome ${response.data.firstName}!`, 'Login successful', {
				timeOut: 2000,
				positionClass: 'toast-top-center'
			}).onHidden.toPromise().then(_ => {
				sessionStorage.setItem('email', response.data.email);
				sessionStorage.setItem('password', response.data.password);
				sessionStorage.setItem('firstName', response.data.firstName);
				sessionStorage.setItem('lastName', response.data.lastName);
				sessionStorage.setItem('id', response.data.id);
				sessionStorage.setItem('lastActive', response.data.lastActive);
				sessionStorage.setItem('userType', 'doctor');
				this.router.navigate(['doctoradvice/profile']);
			});
		} else {
			this.toastr.error('Invalid credentials', 'Login Unsuccessful', {
				timeOut: 2000,
				positionClass: 'toast-top-center'
			});
		}
	}
}
