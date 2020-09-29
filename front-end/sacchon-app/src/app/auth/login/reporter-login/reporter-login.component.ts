import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Chiefs } from 'src/app/interfaces/chiefs';
import { ToastrService } from 'ngx-toastr';

@Component({
	selector: 'sacchon-app-reporter-login',
	templateUrl: './reporter-login.component.html',
	styleUrls: ['./reporter-login.component.scss']
})
export class ReporterLoginComponent implements OnInit {

	constructor(private http: HttpClient, private router: Router, private toastr: ToastrService) { }

	reporterLoginForm = new FormGroup({
		email: new FormControl(null, [Validators.required, Validators.email]),
		password: new FormControl(null, [Validators.required, Validators.minLength(8)]),
	});


	ngOnInit(): void {
	}

	submitLogin(): any {
		this.http.post<Chiefs>('http://localhost:9000/v1/login/chief', {
			email: this.reporterLoginForm.get('email').value,
			password: this.reporterLoginForm.get('password').value
		}).subscribe(response => {
			if (response.status == 200) {
				this.toastr.success(`Welcome ${response.data[0].firstName}!`, 'Login successful', {
					timeOut: 2000,
					positionClass: 'toast-top-center'
				}).onHidden.toPromise().then(_ => {
					sessionStorage.setItem('email', response.data[0].email);
					sessionStorage.setItem('password', response.data[0].password);
					sessionStorage.setItem('firstName', response.data[0].firstName);
					sessionStorage.setItem('lastName', response.data[0].lastName);
					sessionStorage.setItem('id', response.data[0].id);
					sessionStorage.setItem('userType', 'reporter');
					this.router.navigate(['reporter']);
				})
			} else {
				this.toastr.error('Invalid credentials', 'Login Unsuccessful', {
					timeOut: 2000,
					positionClass: 'toast-top-center'
				})
			}
		})
	}
}
