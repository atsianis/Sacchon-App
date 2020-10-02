import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DataTableDirective } from 'angular-datatables';
import * as moment from 'moment';
import { Subject } from 'rxjs';
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

	dtElement: DataTableDirective;
	dtOptions: DataTables.Settings = {};
	dtTrigger: Subject<any> = new Subject();

	ngOnInit(): void {
		this.consultations = [];
		this.getConsultations();
		this.dtOptions = {
			order: [0, 'asc'],
			pagingType: 'full_numbers',
			pageLength: 10,
		};
	}

	getConsultations(): void {
		this.route.params.subscribe(params => {
			this.id = params.patient_id;
		});
		this.doctorService.getPatientConsultations(this.id).subscribe(response => {
			this.consultations = response.data;
			this.dtTrigger.next();
		}, (err) => {
			console.log('-----> err', err);
		});
	}

	consultationFromDate(date: number): string{
		return  `${moment(date).format('MMMM Do')}`;
	}

	consultationToDate(date: number): string{
		return  `${moment(date).add(30, 'days').format('MMMM Do')}`;
	}
}
