import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
	selector: 'sacchon-app-doctor-sign-up',
	templateUrl: './doctor-sign-up.component.html',
	styleUrls: ['./doctor-sign-up.component.scss'],
})
export class DoctorSignUpComponent implements OnInit {
	constructor(private toastr: ToastrService, private http: HttpClient) { }

	doctorSignUp = new FormGroup({
		firstName: new FormControl(null, [Validators.required]),
		lastName: new FormControl(null, [Validators.required]),
		email: new FormControl(null, [Validators.required, Validators.email]),
		password: new FormControl(null, [Validators.required, Validators.minLength(8)]),
		passwordconfirm: new FormControl(null)
	});

	httpOptions = {
		headers: new HttpHeaders({
			'Authorization': 'Basic ' + btoa('asd@asd.asd:asdasdasd'),
			'Access-Control-Allow-Origin': '*'
		})
	};

	ngOnInit(): void { }

	signUp(): void {
		if (this.doctorSignUp.valid && (this.doctorSignUp.get('password').value === this.doctorSignUp.get('passwordconfirm').value)) {
			this.http.post(`http://localhost:9000/v1/create/doctor`, {
				firstName: this.doctorSignUp.get('firstName').value,
				lastName: this.doctorSignUp.get('lastName').value,
				email: this.doctorSignUp.get('email').value,
				password: this.doctorSignUp.get('password').value,
			}, this.httpOptions).subscribe(response => {
				this.toastr.success('You will be redirected to home page soon.', 'Successfully registered', {
					timeOut: 2000,
					positionClass: 'toast-top-center'
				}).onHidden.toPromise().then(_ => {
					location.href = '/doctoradvice/profile';
				});
			})
		} else {
			this.doctorSignUp.markAllAsTouched();
		}
	}
}
