import { Component, OnInit } from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import * as moment from 'moment';
import { Subject } from 'rxjs';
import { ReporterService } from '../reporter.service';

@Component({
  selector: 'sacchon-app-all-patients-db',
  templateUrl: './all-patients-db.component.html',
  styleUrls: ['./all-patients-db.component.scss']
})
export class AllPatientsDbComponent implements OnInit {

  patients: any;
	dtElement: DataTableDirective;
	dtOptions: DataTables.Settings = {};
	dtTrigger: Subject<any> = new Subject();

	constructor(private reporterService: ReporterService) { }

	ngOnInit(): void {
		this.patients = [];
		
		this.getPatientsDB();

		this.dtOptions = {
			order: [0, 'asc'],
			pagingType: 'full_numbers',
			pageLength: 10,		};
	}

	getPatientsDB(): void {
		this.reporterService.getAllPatientsFromDatabase().subscribe(patients => {
			console.log(patients);	
			this.patients = patients.data;
			this.dtTrigger.next();
		}, (err) => {
			console.log('-----> err', err);
		});
	}

	birthDate(date: number): string {
		return moment(date).format('DD/MM/YYYY');
	}

	lastActive(date: number): string {
		return this.reporterService.lastActive(date)
	}
}
