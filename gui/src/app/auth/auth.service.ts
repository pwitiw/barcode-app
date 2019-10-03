import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {RestService} from "../services/rest.service";
import {map} from "rxjs/operators";

@Injectable()
export class AuthService {
    private static readonly LOGIN_ENDPOINT = "/login";
    static readonly TOKEN_KEY = "token";
    static readonly USERNAME_KEY = "username";

    private loggedUser: string;

    constructor(private rest: RestService) {
        this.loggedUser = sessionStorage.getItem(AuthService.USERNAME_KEY);
    }

    public getLoggedUser(): string {
        return this.loggedUser;
    }

    public isAuthenticated(): boolean {
        return this.loggedUser != null;
    }

    public loginUsingCredentials(username: string, password: string): Observable<boolean> {
        let credentials = {
            username: username,
            password: password
        };
        return this.rest.post(AuthService.LOGIN_ENDPOINT, credentials)
            .pipe(
                map(response => {
                    if (response && response.status === 200) {
                        const token = response.headers.get("Authorization");
                        localStorage.setItem(AuthService.TOKEN_KEY, token);
                        localStorage.setItem(AuthService.TOKEN_KEY, token);
                        this.loggedUser = username;
                        return true;
                    }
                    return false;
                })
            );
    }

    public logout(): void {
        localStorage.removeItem(AuthService.TOKEN_KEY);
        localStorage.removeItem(AuthService.USERNAME_KEY);
        this.loggedUser = null;
    }
}
