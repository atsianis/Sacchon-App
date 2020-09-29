import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import * as moment from 'moment';

@Injectable({
  providedIn: 'root'
})
export class DoctorAdviceService {

  constructor(private http: HttpClient) { }

  lastActive(date: number): string {
		if (moment().diff(date, "days") > 0)
			return `${moment().diff(date, "days")} days ago`;
		else
			return `${moment().diff(date, "hours")} hours ago`;
  }
  
  birthDate(date: number): string {
		return moment(date).format('DD/MM/YYYY');
	}
}
