import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import * as moment from 'moment';
import { ToastrService } from 'ngx-toastr';
import { MediDataRepoService } from '../medi-data-repo.service';

@Component({
	selector: 'sacchon-app-consultations',
	templateUrl: './consultations.component.html',
	styleUrls: ['./consultations.component.scss']
})
export class ConsultationsComponent implements OnInit {

	constructor(private route: ActivatedRoute, private patientService: MediDataRepoService, private toastr: ToastrService) { }
	id: string;
	consultations: any;
	returnDate : any;

	ngOnInit(): void {
		this.consultations = []
		this.getConsultations();
	}

	getConsultations(): void {
		this.patientService.getConsultations(sessionStorage.getItem('id')).subscribe(response => {
			if (response.status == 200)
				this.consultations = response.data;
			if (response.status == 404)
				this.toastr.error(response.description, 'Error', {
					timeOut: 2000,
					positionClass: 'toast-top-center'
				})
		}, (err) => {
			console.log('-----> err', err);
		});
	}

	consultationFromDate(date: number): string{
		return  `${moment(date).format('MMMM Do')}`
	}

	consultationToDate(date: number): string{
		return  `${moment(date).add(30, 'days').format('MMMM Do')}`
	}
}
