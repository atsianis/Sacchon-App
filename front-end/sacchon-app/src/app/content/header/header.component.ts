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

	isLoggedIn(): string {
		if (sessionStorage.getItem('firstName'))
		return this.user = sessionStorage.getItem('firstName');
	}

	signOut(): any {
		sessionStorage.clear();
		this.router.navigate(['']);
	}

}
