import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  private currentUser: boolean;

  constructor(public router: Router) {}

  setUser(username: string, token: string) {
    localStorage.setItem('currentUser', username);
    localStorage.setItem('currentUserToken', token);
  }

  logout() {
    localStorage.removeItem('currentUser');
    localStorage.removeItem('currentUserToken');
  }

  canActivate(): boolean {
    if (!localStorage.currentUser) {
      this.router.navigate(['/login']);
    }
    return true;
  }
}
