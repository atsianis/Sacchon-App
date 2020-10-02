import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import * as moment from 'moment';
import { DoctorAdviceService } from '../../doctor-advice.service';

@Component({
	selector: 'sacchon-app-consult-edit',
	templateUrl: './consult-edit.component.html',
	styleUrls: ['./consult-edit.component.scss']
})
export class ConsultEditComponent implements OnInit {

	constructor(private doctorService: DoctorAdviceService, private route: ActivatedRoute, private toastr: ToastrService, private router: Router) { }

	consultation_id: string;
	consultation: any;
	doctor_id: string;
	patient_id: string;
	editConsultForm = new FormGroup({
		comment: new FormControl
	});

	ngOnInit(): void {
		this.findConsutltationById();
	}

	findConsutltationById(): void {
		this.route.params.subscribe(params => {
			this.consultation_id = params.consultation_id;
		});
		this.doctorService.getConsultationById(this.consultation_id).subscribe(response => {
			this.consultation = response.data;
		});
	}

	submitConsultation(): any {
		this.doctor_id = sessionStorage.getItem('id');
		this.doctorService.editConsultation(this.consultation_id, this.doctor_id, this.editConsultForm.get('comment').value).subscribe(response => {
			this.toastr.success('Consultation edited successfully', 'Success', {
				timeOut: 2000,
				positionClass: 'toast-top-center'
			}).onHidden.toPromise().then(_ => {
				this.router.navigate(['/doctoradvice/profile']);
			}).catch(error => {
				console.log(error);
			});
		});
	}

	formatConsultationDate(date: number): string {
		return `Consultation for ${moment(date).format('MMMM Do')} to ${moment(date).add(30, 'days').format('MMMM Do')}`;
	}
}
