import {Injectable} from '@angular/core';
import {Credentials} from 'src/app/auth/Credentials';
import {Observable} from 'rxjs';
import {RestService} from "../services/rest.service";
import {map, tap} from "rxjs/operators";
import {LoadingService} from "../services/loading.service";

@Injectable()
export class AuthService {
    private static readonly LOGIN_ENDPOINT = "/login";
    private static readonly TOKEN_KEY = "token";
    private static readonly USER_KEY = "username";

    constructor(private rest: RestService,
                private loadingService: LoadingService) {
    }

    public getLoggedUser(): string {
        return localStorage.getItem(AuthService.USER_KEY);
    }

    public isAuthenticated(): boolean {
        const token = localStorage.getItem(AuthService.TOKEN_KEY);
        const user = localStorage.getItem(AuthService.USER_KEY);
        return !!token && !!user;
    }

    public login(username: string, password: string): Observable<boolean> {
        this.loadingService.show();
        const credentials: Credentials = {
            username: username,
            password: password
        };
        return this.rest.post(AuthService.LOGIN_ENDPOINT, credentials)
            .pipe(
                tap(() => this.loadingService.hide()),
                map(response => {
                    if (response && response.status === 200) {
                        const token = response.headers.get("Authorization");
                        localStorage.setItem(AuthService.TOKEN_KEY, token);
                        localStorage.setItem(AuthService.USER_KEY, credentials.username);
                        return true;
                    }
                    return false;
                })
            );
    }

    public logout() {
        localStorage.removeItem(AuthService.TOKEN_KEY);
        localStorage.removeItem(AuthService.USER_KEY);
    }
}
