import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ChartDataSets, ChartOptions } from 'chart.js';
import { Color, Label } from 'ng2-charts';
import * as moment from 'moment';

@Component({
	selector: 'sacchon-app-patient-list',
	templateUrl: './patient-list.component.html',
	styleUrls: ['./patient-list.component.scss']
})
export class PatientListComponent implements OnInit {

	constructor(private route: ActivatedRoute, private http: HttpClient) { }
	patient: any;
	// Array of different segments in chart
	lineChartData: ChartDataSets[] = []

	//Labels shown on the x-axis
	lineChartLabels: Label[] = [];

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

	patientGlycose: any = [];
	patientCalories: any = [];
	patientRecordTimestamp: any = [];

	getPatientById(): void {
		const httpOptions = {
			headers: new HttpHeaders({
			  'Authorization': 'Basic ' + btoa('asd@asd.asd:asdasdasd')
			})
		};
		this.route.params.subscribe(params => {
			this.http.get(`http://localhost:9000/v1/patient/${params.id}`, httpOptions).subscribe(patient => {
				this.patient = patient;
				patient.patientRecords.forEach(record => {
					this.patientGlycose.push(record.sacchon)
					this.patientCalories.push(record.calories)
					this.patientRecordTimestamp.push(moment.utc(record.timecreated).format("MM/DD/YYYY"))
				});
			}, (err) => {
				console.log('-----> err', err);
			});
		});
	}

	ngOnInit(): void {
		this.getPatientById();
		this.lineChartData = [
			{ data: this.patientCalories, label: 'Calories (gr)' },
			{ data: this.patientGlycose, label: 'Glycose (ml)' }
		];
		this.lineChartLabels = this.patientRecordTimestamp;
	}

	toProperDate(date): String {
		return moment.utc(date).format("MM/DD/YYYY");
	}

}
