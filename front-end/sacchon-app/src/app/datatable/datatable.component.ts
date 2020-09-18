import { Component, OnInit } from '@angular/core';

@Component({
	selector: 'sacchon-app-datatable',
	templateUrl: './datatable.component.html',
	styleUrls: ['./datatable.component.scss']
})
export class DatatableComponent implements OnInit {

	constructor() { }
	dtOptions: DataTables.Settings = {};

	ngOnInit(): void {
		this.dtOptions = {
			pagingType: 'full_numbers'
		};
	}

}
