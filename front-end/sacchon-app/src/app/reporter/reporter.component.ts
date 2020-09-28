import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
	selector: 'sacchon-app-reporter',
	templateUrl: './reporter.component.html',
	styleUrls: ['./reporter.component.scss']
})
export class ReporterComponent implements OnInit {

	
	constructor(private http: HttpClient) { }

	ngOnInit(): void {
	}

}
