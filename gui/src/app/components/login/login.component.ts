import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {map} from "rxjs/operators";
import {RestService} from "../../services/rest.service";
import {LoggedUserService} from "../../services/logged-user.service";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
    private static readonly LOGIN_ENDPOINT = "/login";

    public username: string;
    public password: string;
    public authFailed: boolean = false;

    constructor(private restService: RestService,
                private router: Router,
                private loggedUserService: LoggedUserService) {
    }

    ngOnInit(): void {
        const loggedUser = this.loggedUserService.getLoggedUser();
        if (loggedUser) {
            this.router.navigate(['/main']);
        }
    }

    public login(event: Event): void {
        event.preventDefault();
        this.isValid() && this.loginUsingCredentials(this.username, this.password).subscribe(result => {
            if (result) {
                this.router.navigate(['/admin/admin-view']);
            }
            this.authFailed = !result;
        });
    }

    loginUsingCredentials(username: string, password: String) {
        let credentials = {
            username: username,
            password: password
        };
        return this.restService.post(LoginComponent.LOGIN_ENDPOINT, credentials)
            .pipe(
                map(response => {
                    if (response && response.status === 200) {
                        const token = response.headers.get("Authorization");
                        this.loggedUserService.login(username, token);
                        return true;
                    }
                    return false;
                })
            );
    }


    handleUsernameChanged(event): void {
        this.username = event;
    }

    handlePasswordChanged(event): void {
        this.password = event;
    }

    private isValid(): boolean {
        return !this.isEmpty(this.username)
            && !this.isEmpty(this.password);
    }

    private isEmpty(arg: string) {
        return !arg || arg.trim() === "";
    }


}
