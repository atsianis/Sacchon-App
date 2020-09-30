import { Component, OnInit } from '@angular/core';
import { ChartDataSets, ChartOptions } from 'chart.js';
import { Color, Label } from 'ng2-charts';
import * as moment from 'moment';

@Component({
	selector: 'sacchon-app-profile',
	templateUrl: './profile.component.html',
	styleUrls: ['./profile.component.scss']
})
export class PatientProfileComponent implements OnInit {

	constructor() { }
	firstName: string = sessionStorage.getItem('firstName');
	lastName: string = sessionStorage.getItem('lastName');
	email: string = sessionStorage.getItem('email');
	dob: string = moment(sessionStorage.getItem('dob')).format('DD/MM/YYYY');
	gender: string = sessionStorage.getItem('gender');

	// Array of different segments in chart
	lineChartData: ChartDataSets[] = [
		{ data: [65, 59, 80, 81, 56, 55, 40], label: 'Carbs (gr)' },
		{ data: [28, 48, 40, 19, 86, 27, 90], label: 'Glycose (ml)' }
	];

	//Labels shown on the x-axis
	lineChartLabels: Label[] = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];

	// Define chart options
	lineChartOptions: ChartOptions = {
		responsive: true
	};

	// Define colors of chart segments
	lineChartColors: Color[] = [

		{ // dark grey
			backgroundColor: 'rgba(77,83,96,0.2)',
			borderColor: 'rgba(77,83,96,1)',
		},
		{ // red
			backgroundColor: 'rgba(255,0,0,0.3)',
			borderColor: 'red',
		}
	];

	// Set true to show legends
	lineChartLegend = true;

	// Define type of chart
	lineChartType = 'line';

	lineChartPlugins = [];

	// events
	chartClicked({ event, active }: { event: MouseEvent, active: {}[] }): void {
		console.log(event, active);
	}

	chartHovered({ event, active }: { event: MouseEvent, active: {}[] }): void {
		console.log(event, active);
	}

	ngOnInit(): void {
	}
}
