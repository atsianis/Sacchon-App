import { Component, OnInit } from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';
import { ReporterService } from '../reporter.service';

@Component({
  selector: 'sacchon-app-all-doctors-db',
  templateUrl: './all-doctors-db.component.html',
  styleUrls: ['./all-doctors-db.component.scss']
})
export class AllDoctorsDbComponent implements OnInit {

  doctors: any;
  dtElement: DataTableDirective;
	dtOptions: DataTables.Settings = {};
	dtTrigger: Subject<any> = new Subject();
	constructor(private reporterService: ReporterService) { }

	ngOnInit(): void {
		this.doctors = [];
		this.getDoctorsDB();
		this.dtOptions = {
			pagingType: 'full_numbers',
			pageLength: 10,			order: [0, 'asc'],
		};
	}

	getDoctorsDB(): void {
		this.reporterService.getAllDoctorsFromDatabase().subscribe(doctors => {
			this.doctors = doctors.data;
			this.dtTrigger.next();
		}, (err) => {
			console.log('-----> err', err);
		});
	}
}
