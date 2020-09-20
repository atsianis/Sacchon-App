import { Component, OnInit } from '@angular/core';

@Component({
	selector: 'sacchon-app-inspect-doctor-list',
	templateUrl: './inspect-doctor-list.component.html',
	styleUrls: ['./inspect-doctor-list.component.scss']
})
export class InspectDoctorListComponent implements OnInit {
	dtOptions: DataTables.Settings = {};

	constructor() { }

	ngOnInit(): void {
		this.dtOptions = {
			pagingType: 'full_numbers'
		};
	}

}
