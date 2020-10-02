import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { DoctorAdviceService } from '../../doctor-advice.service';

@Component({
	selector: 'sacchon-app-add-consult',
	templateUrl: './add-consult.component.html',
	styleUrls: ['./add-consult.component.scss']
})
export class AddConsultComponent implements OnInit {

	constructor(private doctorService: DoctorAdviceService, private route: ActivatedRoute, private toastr: ToastrService, private router: Router) { }

	createConsultForm = new FormGroup({
		comment: new FormControl(null, [Validators.required])
	});

	ngOnInit(): void {
	}

	submitConsultation(): any {
		const doctor_id = sessionStorage.getItem('id');
		const comment = this.createConsultForm.get('comment').value;

		this.route.params.subscribe(params => {
			this.doctorService.addConsultation(doctor_id, params.patient_id, comment).subscribe(response => {
				if (response.status == 200) {
					this.toastr.success('Consultation created successfully', 'Success', {
						timeOut: 2000,
						positionClass: 'toast-top-center'
					}).onHidden.toPromise().then(_ => {
						this.router.navigate(['/doctoradvice/patient', params.patient_id]);
					}).catch(error => {
						console.log(error);
					});
				}
				if (response.status == 401 || response.status == 422) {
					this.toastr.error(response.description, 'Fail', {
						timeOut: 2000,
						positionClass: 'toast-top-center'
					}).onHidden.toPromise().then(_ => {
						this.router.navigate(['/doctoradvice/patient', params.patient_id]);
					}).catch(error => {
						console.log(error);
					});
				}

			});
		});
	}
}
