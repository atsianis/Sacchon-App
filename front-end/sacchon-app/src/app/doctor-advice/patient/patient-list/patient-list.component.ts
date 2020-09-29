import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ChartDataSets, ChartOptions } from 'chart.js';
import { Color, Label } from 'ng2-charts';
import * as moment from 'moment';
import { Patients } from 'src/app/interfaces/patients';
import { DoctorAdviceService } from '../../doctor-advice.service';

@Component({
	selector: 'sacchon-app-patient-list',
	templateUrl: './patient-list.component.html',
	styleUrls: ['./patient-list.component.scss']
})
export class PatientListComponent implements OnInit {

	patientGlycose: any = [];
	patientCarbs: any = [];
	patientRecordTimestamp: any = [];

	constructor(private route: ActivatedRoute, private doctorService: DoctorAdviceService) { }
	patient: Patients;
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
			this.doctorService.getPatientById(params.id).subscribe(patient => {
				this.patient = patient.data;
				this.getCurrentPatientRecords(patient.data)
			}, (err) => {
				console.log('-----> err', err);
			});
		});
	}

	getCurrentPatientRecords(patient: Patients): void {
		this.doctorService.getCurrentPatientRecords(patient.id).subscribe(patientRecords => {
		patientRecords.data.forEach(patientRecord => {
				this.patientCarbs.push(patientRecord.carbs)
				this.patientGlycose.push(patientRecord.glycose)
			});
		})
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
