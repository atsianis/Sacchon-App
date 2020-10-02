import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { DoctorAdviceService } from '../doctor-advice.service';

@Component({
	selector: 'sacchon-app-edit-profile',
	templateUrl: './edit-profile.component.html',
	styleUrls: ['./edit-profile.component.scss']
})
export class EditProfileComponent implements OnInit {

	constructor(private router: Router, private toastr: ToastrService, private doctorService: DoctorAdviceService, private modalService: NgbModal) { }

	id = sessionStorage.getItem('id');
	firstName = sessionStorage.getItem('firstName');
	lastName = sessionStorage.getItem('lastName');
	email = sessionStorage.getItem('email');
	password = sessionStorage.getItem('password');
	isDeleted = sessionStorage.getItem('isDeleted');

	modal = new FormControl;

	doctorEdit = new FormGroup({
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
		const firstName = this.doctorEdit.get('firstName').value;
		const lastName = this.doctorEdit.get('lastName').value;
		const email = this.doctorEdit.get('email').value;
		const password = this.doctorEdit.get('password').value;

		if (this.doctorEdit.valid && (this.doctorEdit.get('password').value === this.doctorEdit.get('passwordconfirm').value)) {
			this.doctorService.editDoctorProfile(this.id, firstName, lastName, email, password).subscribe(response => {
				if (response.status == 200) {
					console.log('response');
					this.toastr.success('You will be redirected to your dashboard soon.', 'Successfully edited info', {
						timeOut: 2000,
						positionClass: 'toast-top-center'
					}).onHidden.toPromise().then(_ => {
						this.router.navigate(['/doctoradvice/profile/']);
					});
				}
				if (response.status == 422 || response.status == 404) {
					console.log('response');
					this.toastr.error(response.description, 'Failed', {
						timeOut: 2000,
						positionClass: 'toast-top-center'
					}).onHidden.toPromise().then(_ => {
						this.router.navigate(['/doctoradvice/profile/']);
					});
				}

			});
		} else {
			this.doctorEdit.markAllAsTouched();
		}
	}

	initializeForm(): void {
		this.doctorEdit = new FormGroup({
			firstName: new FormControl(this.firstName, [Validators.required]),
			lastName: new FormControl(this.lastName, [Validators.required]),
			email: new FormControl(this.email, [Validators.required, Validators.email]),
			password: new FormControl(null, [Validators.required, Validators.minLength(8)]),
			passwordconfirm: new FormControl(null)
		});
	}

	cancel(): void {
		this.router.navigate(['/doctoradvice/profile/']);
	}

	open(content) {
		this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' });
	}

	softDelete(): void {
		this.doctorService.softDelete(this.id).subscribe(response => {
			this.toastr.success('You will be redirected to home page soon.', 'Successfully Deleted Account', {
				timeOut: 2000,
				positionClass: 'toast-top-center'
			}).onHidden.toPromise().then(_ => {
				this.router.navigate(['']);
				sessionStorage.clear();
			});
		});
	}
}
