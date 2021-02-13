import {Component, DoCheck} from '@angular/core';
import {LoggedUserService} from "../../services/logged-user.service";

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html'
})
export class NavbarComponent implements DoCheck {

    loggedUser: string;

    constructor(private loggedUserService: LoggedUserService) {
    }

    ngDoCheck(): void {
        const loggedUser = this.loggedUserService.getLoggedUser();
        this.loggedUser = loggedUser ? loggedUser.toLocaleUpperCase() : "";
    }

    logout(): void {
        this.loggedUserService.logout();
    }

    handleTabChanged(): void {
        window.scroll(0,0);
    }

    isAuthenticated(): boolean {
        return !!this.loggedUser;
    }
}
