import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import * as moment from 'moment';
import { DoctorAdviceService } from '../doctor-advice.service';

@Component({
	selector: 'sacchon-app-consult',
	templateUrl: './consult.component.html',
	styleUrls: ['./consult.component.scss']
})
export class ConsultComponent implements OnInit {

	consultations: any;
	id: number;

	constructor(private doctorService: DoctorAdviceService, private route: ActivatedRoute) { }

	ngOnInit(): void {
		this.consultations = [];
		this.getConsultations();
	}

	getConsultations(): void {
		this.route.params.subscribe(params => {
			this.id = params.patient_id;
		});
		this.doctorService.getPatientConsultations(this.id).subscribe(response => {
			this.consultations = response.data;
		}, (err) => {
			console.log('-----> err', err);
		});
	}

	formatConsultationDate(date: number): string {
		return `Consultation for ${moment(date).format('MMMM Do')} to ${moment(date).add(30, 'days').format('MMMM Do')}`;
	}
}
