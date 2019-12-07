import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {LoggedUserService} from "../services/logged-user.service";

@Injectable({
    providedIn: 'root'
})
export class AuthGuardService implements CanActivate {


    constructor(private loggedUser: LoggedUserService, private router: Router) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        const loggedUser = this.loggedUser.getLoggedUser();
        if (!loggedUser) {
            this.router.navigate(['/login'], {queryParams: {returnUrl: state.url}});
            return false;
        }
        // if (!this.auth.isAuthenticated()) {
        //     this.router.navigate(['/loginUsingCredentials']);
        //     return of(false);
        // }
        // return of(true);
        // this.router.navigate(['/loginUsingCredentials']);
        // this.router.navigate(['/login'], {queryParams: {returnUrl: state.url}});
        return true;
    }

}
