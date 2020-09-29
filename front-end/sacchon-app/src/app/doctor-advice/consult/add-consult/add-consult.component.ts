import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
	selector: 'sacchon-app-add-consult',
	templateUrl: './add-consult.component.html',
	styleUrls: ['./add-consult.component.scss']
})
export class AddConsultComponent implements OnInit {


	constructor(private http: HttpClient, private route: ActivatedRoute, private toastr: ToastrService, private router: Router) { }

	createConsultForm = new FormGroup({
		comment: new FormControl(null, [Validators.required])
	})

	doctor_id: any;
	patient_id: any;

	ngOnInit(): void {
	}

	submitConsultation(): any {
		this.doctor_id = sessionStorage.getItem('id');
		this.route.params.subscribe(params => {
			this.patient_id = params.patient_id
		})
		this.http.post(`http://localhost:9000/v1/consultation/doctor/${this.doctor_id}/patient/${this.patient_id}`, {
			comment: this.createConsultForm.get('comment').value
		}).subscribe(response => {
			this.toastr.success('Consultation created successfully', 'Success', {
				timeOut: 2000,
				positionClass: 'toast-top-center'
			}).onHidden.toPromise().then(_ => {
				this.router.navigate(['/doctoradvice/profile']);
			}).catch(error => {
				console.log(error);
			});
		})
	}

}
