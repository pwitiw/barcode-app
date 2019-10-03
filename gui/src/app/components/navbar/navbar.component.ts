import {Component, DoCheck} from '@angular/core';
import {AuthService} from "../../auth/auth.service";

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html'
})
export class NavbarComponent implements DoCheck {

    loggedUser: string;

    constructor(private authService: AuthService) {
    }

    logout(): void {
        this.authService.logout();
    }

    isAuthenticated(): boolean {
        return this.authService.isAuthenticated();
    }

    ngDoCheck(): void {
        const loggedUser = this.authService.getLoggedUser();
        this.loggedUser = loggedUser ? loggedUser.toLocaleUpperCase() : "";
    }
}
