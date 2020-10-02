import { Component, OnInit } from '@angular/core';
import { PatientRecords } from 'src/app/interfaces/patient-records';
import { MediDataRepoService } from '../medi-data-repo.service';
import * as moment from 'moment';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
	selector: 'sacchon-app-edit-patient-record',
	templateUrl: './edit-patient-record.component.html',
	styleUrls: ['./edit-patient-record.component.scss']
})
export class EditPatientRecordComponent implements OnInit {

	constructor(private patientService: MediDataRepoService, private route: ActivatedRoute, private toastr: ToastrService, private router: Router) { }

	patientRecord: PatientRecords;
	patient_id: string = sessionStorage.getItem('id');
	record_id: string;

	editPatientRecord: FormGroup = new FormGroup({
		glycose: new FormControl(null, Validators.required),
		carbs: new FormControl(null, Validators.required)
	});

	ngOnInit(): void {
		this.getCurrentRecord();
	}

	getCurrentRecord(): void {
		this.route.params.subscribe(params => {
			this.record_id = params.id;
		});
		this.patientService.getPatientRecordById(this.patient_id, this.record_id).subscribe(response => {
			this.patientRecord = response.data;

			// initialize form
			this.editPatientRecord = new FormGroup ({
				glycose: new FormControl(response.data.glycose, Validators.required),
				carbs: new FormControl(response.data.carbs, Validators.required)
			});
		});

	}

	editRecord(): void {
		this.patientService.editPatientRecord(this.patient_id, this.record_id, this.editPatientRecord.get('glycose').value, this.editPatientRecord.get('carbs').value).subscribe(response => {
			console.log(response);
			if (response.status == 200) {
				this.toastr.success('Record successfully edited.', 'Success', {
					timeOut: 2000,
					positionClass: 'toast-top-center'
				}).onHidden.toPromise().then(_ => {
					this.router.navigate(['/medidatarepo/profile']);
				}).catch(error => {
					console.log(error);
				});
			}
		});
	}

	formatPatientRecordDate(date: number) {
		return moment(date).format('DD/MM/YYYY, h:mm:ss a');
	}

}
