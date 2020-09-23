import { Component, OnInit } from '@angular/core';

@Component({
	selector: 'sacchon-app-patient',
	templateUrl: './patient.component.html',
	styleUrls: ['./patient.component.scss']
})
export class PatientComponent implements OnInit {

	constructor() { }
	dtOptions: DataTables.Settings = {};

	ngOnInit(): void {
		this.dtOptions = {
			pagingType: 'full_numbers'
		};
	}

}
