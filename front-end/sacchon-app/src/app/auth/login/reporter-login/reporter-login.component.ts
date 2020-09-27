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

	httpOptions = {
		headers: new HttpHeaders({
			'Authorization': 'Basic ' + btoa('asd@asd.asd:asdasdasd'),
			'Access-Control-Allow-Origin': '*'
		})
	};

	ngOnInit(): void {
	}

	submitLogin(): any {
		this.http.post<Chiefs>('http://localhost:9000/v1/login/chief', {
			email: this.reporterLoginForm.get('email').value,
			password: this.reporterLoginForm.get('password').value
		}, this.httpOptions).subscribe(response => {
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
