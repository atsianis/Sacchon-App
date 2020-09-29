import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ChartDataSets, ChartOptions } from 'chart.js';
import { Color, Label } from 'ng2-charts';
import * as moment from 'moment';
import { Patients } from 'src/app/interfaces/patients';

@Component({
	selector: 'sacchon-app-patient-list',
	templateUrl: './patient-list.component.html',
	styleUrls: ['./patient-list.component.scss']
})
export class PatientListComponent implements OnInit {

	patientGlycose: any = [];
	patientCarbs: any = [];
	patientRecordTimestamp: any = [];

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


	getPatientById(): void {
		this.route.params.subscribe(params => {
			this.http.get<Patients>(`http://localhost:9000/v1/patient/${params.id}`).subscribe(patient => {
				this.patient = patient.data;
				this.patient.patientRecords.forEach(record => {
					this.patientGlycose.push(record.glycose)
					this.patientCarbs.push(record.carbs)
					this.patientRecordTimestamp.push(moment.utc(record.timecreated).format("MM/DD/YYYY, h:mm:ss a"))
				});
			}, (err) => {
				console.log('-----> err', err);
			});
		});
	}

	ngOnInit(): void {
		this.getPatientById();
		this.lineChartData = [
			{ data: this.patientCarbs, label: 'Carbs (gr)' },
			{ data: this.patientGlycose, label: 'Glycose (ml)' }
		];
		this.lineChartLabels = this.patientRecordTimestamp;
	}

	toProperDate(date): string {
		return moment.utc(date).format('MM/DD/YYYY');
	}

}
