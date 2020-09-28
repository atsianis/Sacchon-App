import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';

@Component({
  selector: 'sacchon-app-inspect-non-active',
  templateUrl: './inspect-non-active.component.html',
  styleUrls: ['./inspect-non-active.component.scss']
})
export class InspectNonActiveComponent implements OnInit {

  doctors: any;
  patients: any;
	dtElement: DataTableDirective;
	dtDoctorOptions: DataTables.Settings = {};
	dtDoctorTrigger: Subject<any> = new Subject();
	dtPatientOptions: DataTables.Settings = {};
	dtPatientTrigger: Subject<any> = new Subject();
  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.doctors = [];
    this.patients = [];
		this.getInactiveDoctors();
		this.getInactivePatients();
		this.dtDoctorOptions = {
			pagingType: 'full_numbers',
			pageLength: 5,
			order: [0, 'asc'],
		};
		this.dtPatientOptions = {
			pagingType: 'full_numbers',
			pageLength: 5,
			order: [0, 'asc'],
		};
  }

  getInactiveDoctors(): void {
    this.http.get('http://localhost:9000/v1/chief/inactivedoctors').subscribe(doctors => {
      this.doctors = doctors;
      this.dtDoctorTrigger.next();
    }, (err) => {
      console.log('-----> err', err);
    });
  }

  getInactivePatients(): void {
    this.http.get('http://localhost:9000/v1/chief/inactivepatients').subscribe(patients => {
      this.patients = patients;
      this.dtPatientTrigger.next();
    }, (err) => {
      console.log('-----> err', err);
    });
  }

}
