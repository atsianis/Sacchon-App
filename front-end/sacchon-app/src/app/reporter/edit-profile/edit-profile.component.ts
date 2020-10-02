import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ReporterService } from '../reporter.service';

@Component({
	selector: 'sacchon-app-edit-profile',
	templateUrl: './edit-profile.component.html',
	styleUrls: ['./edit-profile.component.scss']
})
export class EditProfileComponent implements OnInit {

	constructor(private router: Router, private toastr: ToastrService, private reporterService: ReporterService) { }

	firstName = sessionStorage.getItem('firstName');
	lastName = sessionStorage.getItem('lastName');
	email = sessionStorage.getItem('email');
	password = sessionStorage.getItem('password');

	reporterEdit = new FormGroup({
		firstName: new FormControl(null, [Validators.required]),
		lastName: new FormControl(null, [Validators.required]),
		email: new FormControl(null, [Validators.required, Validators.email]),
		password: new FormControl(null, [Validators.required, Validators.minLength(8)]),
		passwordconfirm: new FormControl(null)
	});


	ngOnInit(): void {
		this.initializeForm();
	 }

	edit(): void {
		const firstName = this.reporterEdit.get('firstName').value;
		const lastName = this.reporterEdit.get('lastName').value;
		const email = this.reporterEdit.get('email').value;
		const password = this.reporterEdit.get('password').value;

		this.reporterService.editProfile(firstName, lastName, email, password).subscribe(response => {
			console.log(response);
			// this.toastr.success('You will be redirected to your dashboard soon.', 'Successfully edited info', {
			// 	timeOut: 2000,
			// 	positionClass: 'toast-top-center'
			// }).onHidden.toPromise().then(_ => {
			// 	this.router.navigate(['/reporter'])
			// });
		});
	}

	cancel(): void {
		this.router.navigate(['/reporter']);
	}

	initializeForm(): void {
		this.reporterEdit = new FormGroup({
			firstName: new FormControl(this.firstName, [Validators.required]),
			lastName: new FormControl(this.lastName, [Validators.required]),
			email: new FormControl(this.email, [Validators.required, Validators.email]),
			password: new FormControl(null, [Validators.required, Validators.minLength(8)]),
			passwordconfirm: new FormControl(null)
		});
	}
}
