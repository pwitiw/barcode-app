import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthService} from "./auth.service";

@Injectable({
    providedIn: 'root'
})
export class AuthGuardService implements CanActivate {


    constructor(private authService: AuthService, private router: Router) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        if (!this.authService.isAuthenticated()) {
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
