import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
	selector: 'sacchon-app-header',
	templateUrl: './header.component.html',
	styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

	constructor(private router: Router) { }

	user: string = null;

	ngOnInit(): void {
		this.isLoggedIn();
	}

	isLoggedIn(): boolean {
		return sessionStorage.getItem('firstName') !== null;
	}

	signOut(): any {
		sessionStorage.removeItem('email');
		sessionStorage.removeItem('password');
		sessionStorage.removeItem('firstName');
		sessionStorage.removeItem('lastName');
		sessionStorage.removeItem('id');
		this.router.navigate(['']);
	}

}
