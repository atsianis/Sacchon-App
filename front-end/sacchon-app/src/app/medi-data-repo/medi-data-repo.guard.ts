import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth.service';

@Injectable({
	providedIn: 'root'
})
export class MediDataRepoGuard implements CanActivate {
	constructor(private router: Router, private auth: AuthService) { }
	canActivate(
		route: ActivatedRouteSnapshot,
		state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
		if (this.auth.isLoggedIn() && sessionStorage.getItem('userType') == 'patient') {
			return true;
		}

		this.router.navigate(['/unauthorized']);
		return false;
	}
}
