import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DataTableDirective } from 'angular-datatables';
import * as moment from 'moment';
import { ToastrService } from 'ngx-toastr';
import { Subject } from 'rxjs';
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
	returnDate: any;

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
		this.patientService.getConsultations(sessionStorage.getItem('id')).subscribe(response => {
			if (response.status === 200) {
				this.consultations = response.data;
			}
			if (response.status === 404) {
				this.toastr.error(response.description, 'Error', {
					timeOut: 2000,
					positionClass: 'toast-top-center'
				});
			}
		}, (err) => {
			console.log('-----> err', err);
		});
	}

	timeSeen(date): string {
		return moment(parseInt(date)).format('MMMM Do YYYY, h:mm:ss a');
	}

	markAsRead(consultation_id): void {
		this.patientService.markConsultationAsRead(sessionStorage.getItem('id'), consultation_id).subscribe(response => {
			this.toastr.success('Marked as read.', 'success', {
				timeOut: 2000,
				positionClass: 'toast-top-center'
			}).onHidden.toPromise().then(_ => {
				location.reload();
			});
		});
	}

	consultationFromDate(date: number): string{
		return  `${moment(date).format('MMMM Do')}`;
	}

	consultationToDate(date: number): string{
		return  `${moment(date).add(30, 'days').format('MMMM Do')}`;
	}

}
