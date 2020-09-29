import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'sacchon-app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.scss']
})
export class EditProfileComponent implements OnInit {

  constructor(private toastr: ToastrService, private http: HttpClient) { }

  firstName = sessionStorage.getItem('firstName');
  lastName = sessionStorage.getItem('lastName');
  email = sessionStorage.getItem('email');
  password = sessionStorage.getItem('password');

	reporterEdit = new FormGroup({
		firstame: new FormControl(null, [Validators.required]),
		lastName: new FormControl(null, [Validators.required]),
		email: new FormControl(null, [Validators.required, Validators.email]),
		password: new FormControl(null, [Validators.required, Validators.minLength(8)]),
		passwordconfirm: new FormControl(null)
	});


	ngOnInit(): void {
		console.log(this.firstName);
		console.log(this.lastName);
	 }

	edit(): void {
		if (this.reporterEdit.valid && (this.reporterEdit.get('password').value === this.reporterEdit.get('passwordconfirm').value)) {
			this.http.put(`http://localhost:9000/v1/chief/settings/update`, {
        		firstName: this.reporterEdit.get('firstName').value,
				lastName: this.reporterEdit.get('lastName').value,
				email: this.reporterEdit.get('email').value,
				password: this.reporterEdit.get('password').value,
			}).subscribe(response => {
				this.toastr.success('You will be redirected to your dashboard soon.', 'Successfully edited info', {
					timeOut: 2000,
					positionClass: 'toast-top-center'
				}).onHidden.toPromise().then(_ => {
					location.href = '/reporter';
				});
			})
		} else {
			this.reporterEdit.markAllAsTouched();
		}
	}
}
