import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'app/services/authentiaction/authentication.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  constructor(public authService: AuthenticationService) {   }

  logout() {
    this.authService.logout();
  }

  ngOnInit() {
  }

}
