import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';
import * as moment from 'moment';
import { ReporterService } from '../../reporter.service';

@Component({
	selector: 'sacchon-app-inspect-doctor-list',
	templateUrl: './inspect-doctor-list.component.html',
	styleUrls: ['./inspect-doctor-list.component.scss']
})
export class InspectDoctorListComponent implements OnInit {

	constructor(private route: ActivatedRoute, private reporterService: ReporterService) { }

	dtElement: DataTableDirective;
	dtOptions: DataTables.Settings = {};
	dtTrigger: Subject<any> = new Subject();
	doctor: any;
	consultations: any;

	ngOnInit(): void {
		this.dtOptions = {
			pagingType: 'full_numbers',
			pageLength: 10,
			order: [0, 'asc'],
		};
		this.getDoctorById();
		this.getConsultations();
	}

	getDoctorById(): void {
		this.route.params.subscribe(params => {
			this.reporterService.getDoctorById(params.id).subscribe(doctor => {
				this.doctor = doctor.data;
			}, (err) => {
				console.log('-----> err', err);
			});
		});
	}

	getConsultations(): void {
		this.route.params.subscribe(params => {
			this.reporterService.getCurrentDoctorConsultations(params.id).subscribe(consultations => {
				this.consultations = consultations.data;
				this.dtTrigger.next();
			}, (err) => {
				console.log('-----> err', err);
			});
		});
	}

	birthDate(date: number): string {
		return moment(date).format('DD/MM/YYYY');
	}
}
