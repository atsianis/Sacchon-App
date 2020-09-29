import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Consultations } from 'src/app/interfaces/consultations';
import * as moment from 'moment';

@Component({
	selector: 'sacchon-app-consult',
	templateUrl: './consult.component.html',
	styleUrls: ['./consult.component.scss']
})
export class ConsultComponent implements OnInit {

	consultations: any;
	id: number;

	constructor(private http: HttpClient, private route: ActivatedRoute) { }

	ngOnInit(): void {
		this.consultations = [];
		this.getConsultations();
	}

	getConsultations(): void {
		this.route.params.subscribe(params => {
			this.id = params.patient_id
		})
		this.http.get<Consultations>(`http://localhost:9000/v1/patient/${this.id}/consultations`).subscribe(response => {
			this.consultations = response.data;
		}, (err) => {
			console.log('-----> err', err);
		});
	}

	formatConsultationDate(date: number): string {
		return `Consultation for ${moment(date).format('MMMM Do')} to ${moment(date).add(30, 'days').format('MMMM Do')}`
	}
}
