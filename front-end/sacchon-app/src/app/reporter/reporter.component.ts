import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
	selector: 'sacchon-app-reporter',
	templateUrl: './reporter.component.html',
	styleUrls: ['./reporter.component.scss']
})
export class ReporterComponent implements OnInit {


	constructor(private http: HttpClient, private router: Router) { }

	firstName: string = sessionStorage.getItem('firstName');
  	lastName: string = sessionStorage.getItem('lastName');
  	email: string = sessionStorage.getItem('email');
  	password: string = sessionStorage.getItem('password');

	ngOnInit(): void {

	}

}
