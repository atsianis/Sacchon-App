import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
	selector: 'sacchon-app-header',
	templateUrl: './header.component.html',
	styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

	constructor(private router: Router, private auth: AuthService) { }

	user: string = null;

	ngOnInit(): void {
		this.isLoggedIn();
	}

	isLoggedIn(): string {
		if (sessionStorage.getItem('firstName'))
			return this.user = sessionStorage.getItem('firstName');
	}

	signOut(): void {
		sessionStorage.clear();
		this.router.navigate(['']);
	}

	redirectToHome(): Promise<boolean> {
		if (!this.auth.isLoggedIn)
			return this.router.navigate([''])
		if (this.auth.isLoggedIn && sessionStorage.getItem('userType') === 'reporter')
			return this.router.navigate(['reporter'])
		if (this.auth.isLoggedIn && sessionStorage.getItem('userType') === 'doctor')
			return this.router.navigate(['doctoradvice/profile'])
		if (this.auth.isLoggedIn && sessionStorage.getItem('userType') === 'patient')
			return this.router.navigate(['medidatarepo/profile'])
	}

}
