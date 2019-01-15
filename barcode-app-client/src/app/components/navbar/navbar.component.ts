import { Component, OnInit } from '@angular/core';
import { AuthGuardService } from 'src/app/services/auth-guard/auth-guard.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html'
})
export class NavbarComponent implements OnInit {

  constructor(private authGuard: AuthGuardService) { }

  ngOnInit() {
  }

  logout() {
    this.authGuard.logout();
  }

}
