import { Component, OnInit } from '@angular/core';
import { Doctors } from 'src/app/interfaces/doctors';
import { ReporterService } from '../reporter.service';

@Component({
	selector: 'sacchon-app-inspect-doctor',
	templateUrl: './inspect-doctor.component.html',
	styleUrls: ['./inspect-doctor.component.scss']
})
export class InspectDoctorComponent implements OnInit {

	doctors: [];

	constructor(private reporterService: ReporterService) { }
	dtOptions: DataTables.Settings = {};

	ngOnInit(): void {
		this.dtOptions = {
			pagingType: 'full_numbers',
			pageLength: 5,
			order: [1, 'desc']
		};
		this.getDoctors();
	}

	getDoctors(): void {
		this.reporterService.getDoctors().subscribe(doctors => {
			this.doctors = doctors.results;
			console.log(this.doctors);
		});
	}
}
