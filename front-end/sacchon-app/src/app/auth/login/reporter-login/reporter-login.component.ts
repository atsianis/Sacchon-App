import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../../auth.service';

@Component({
	selector: 'sacchon-app-reporter-login',
	templateUrl: './reporter-login.component.html',
	styleUrls: ['./reporter-login.component.scss']
})
export class ReporterLoginComponent implements OnInit {

	constructor(private authService: AuthService, private router: Router, private toastr: ToastrService) { }

	reporterLoginForm = new FormGroup({
		email: new FormControl(null, [Validators.required, Validators.email]),
		password: new FormControl(null, [Validators.required, Validators.minLength(8)]),
	});


	ngOnInit(): void {
	}

	submitLogin(): any {
		const email = this.reporterLoginForm.get('email').value
		const password = this.reporterLoginForm.get('password').value

		this.authService.reporterLogin(email, password).subscribe(response => {
			this.handleLogin(response)
		})
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
				sessionStorage.setItem('userType', 'reporter');
				this.router.navigate(['reporter']);
			})
		} else {
			this.toastr.error('Invalid credentials', 'Login Unsuccessful', {
				timeOut: 2000,
				positionClass: 'toast-top-center'
			})
		}
	}
}
