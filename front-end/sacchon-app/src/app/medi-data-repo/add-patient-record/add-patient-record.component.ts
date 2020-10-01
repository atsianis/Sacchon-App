import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { MediDataRepoService } from '../medi-data-repo.service';

@Component({
	selector: 'sacchon-app-add-patient-record',
	templateUrl: './add-patient-record.component.html',
	styleUrls: ['./add-patient-record.component.scss']
})
export class AddPatientRecordComponent implements OnInit {

	constructor(private patientService: MediDataRepoService, private toastr: ToastrService, private router: Router) { }

	addPatientRecord = new FormGroup({
		glycose: new FormControl(null, Validators.required),
		carbs: new FormControl(null, Validators.required)
	})

	ngOnInit(): void {
	}

	submitRecord(): void {
		const glycose = this.addPatientRecord.get('glycose').value;
		const carbs = this.addPatientRecord.get('carbs').value;
		const patient_id = sessionStorage.getItem('id');

		this.patientService.submitPatientRecord(patient_id, glycose, carbs).subscribe(response => {
			if (response.status == 200) {
				this.toastr.success('Record successfully inserted.', 'Success', {
					timeOut: 2000,
					positionClass: 'toast-top-center'
				}).onHidden.toPromise().then(_ => {
					this.router.navigate(['/medidatarepo/profile']);
				}).catch(error => {
					console.log(error);
				});
			}
			if ( response.status == 422 || response.status == 404 ) {
				this.toastr.error(response.description, 'Failed', {
					timeOut: 2000,
					positionClass: 'toast-top-center'
				}).onHidden.toPromise().then(_ => {
					this.router.navigate(['/medidatarepo/profile']);
				}).catch(error => {
					console.log(error);
				});
			}
		})
	}
}
