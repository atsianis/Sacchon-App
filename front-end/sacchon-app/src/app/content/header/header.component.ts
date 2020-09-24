import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'sacchon-app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  constructor() { }

  user: string = null;

  ngOnInit(): void {
    this.isLoggedIn()
  }

  isLoggedIn(): boolean {
    this.user = sessionStorage.getItem('email')
    console.log(this.user !== null)
      return this.user !== null 
  }

  signOut(): any {
    sessionStorage.removeItem('email')
    sessionStorage.removeItem('password')
    location.reload();
  }

}
