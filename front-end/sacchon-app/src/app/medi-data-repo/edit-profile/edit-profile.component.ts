import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import * as moment from 'moment';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { MediDataRepoService } from '../medi-data-repo.service';

@Component({
	selector: 'sacchon-app-edit-profile',
	templateUrl: './edit-profile.component.html',
	styleUrls: ['./edit-profile.component.scss']
})
export class PatientEditProfileComponent implements OnInit {

	constructor(private router: Router, private toastr: ToastrService, private http: HttpClient, private patientService: MediDataRepoService, private modalService: NgbModal) { }

	id = sessionStorage.getItem('id');
	firstName = sessionStorage.getItem('firstName');
	lastName = sessionStorage.getItem('lastName');
	email = sessionStorage.getItem('email');
	dob = moment(parseInt(sessionStorage.getItem('dob'))).format("DD/MM/YYYY");
	password = sessionStorage.getItem('password');
	isDeleted = sessionStorage.getItem('isDeleted');
	doctor_id = sessionStorage.getItem('doctor_id');

	modal = new FormControl;

	patientEdit = new FormGroup({
		firstName: new FormControl(null, [Validators.required]),
		lastName: new FormControl(null, [Validators.required]),
		email: new FormControl(null, [Validators.required, Validators.email]),
		dob: new FormControl(null, [Validators.required]),
		password: new FormControl(null, [Validators.required, Validators.minLength(8)]),
		passwordconfirm: new FormControl(null)
	});


	ngOnInit(): void {
		this.initializeForm()
	}

	edit(): void {
		const firstName = this.patientEdit.get('firstName').value
		const lastName = this.patientEdit.get('lastName').value
		const email = this.patientEdit.get('email').value
		const dob = this.patientEdit.get('dob').value
		const password = this.patientEdit.get('password').value
		this.patientService.editProfile(this.id, firstName, lastName, email, dob, password).subscribe(response => {
			console.log(response)
			this.toastr.success('You will be redirected to your dashboard soon.', 'Successfully edited info', {
				timeOut: 2000,
				positionClass: 'toast-top-center'
			}).onHidden.toPromise().then(_ => {
				this.router.navigate(['/medidatarepo/profile/']);
			});
		})
	}

	initializeForm(): void {
		this.patientEdit = new FormGroup({
			firstName: new FormControl(this.firstName, [Validators.required]),
			lastName: new FormControl(this.lastName, [Validators.required]),
			email: new FormControl(this.email, [Validators.required, Validators.email]),
			dob: new FormControl(this.dob, [Validators.required]),
			password: new FormControl(null, [Validators.required, Validators.minLength(8)]),
			passwordconfirm: new FormControl(null)
		});
	}

	cancel(): void {
		this.router.navigate(['/medidatarepo/profile/'])
	}

	open(content) {
		this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' })
	}


	softDelete(): void {
		this.patientService.softDelete(this.id).subscribe(response => {
			console.log(response)
			this.toastr.success('You will be redirected to home page soon.', 'Successfully Deleted Account', {
				timeOut: 2000,
				positionClass: 'toast-top-center'
			}).onHidden.toPromise().then(_ => {
				this.router.navigate(['']);
				sessionStorage.clear();
			});
		})
	}
}
