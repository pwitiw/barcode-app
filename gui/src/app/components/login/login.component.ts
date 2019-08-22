import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from 'src/app/auth/auth.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
    public username: string;
    public password: string;
    public authFailed: boolean = false;

    constructor(private authService: AuthService,
                private router: Router) {
    }

    ngOnInit(): void {
        if (this.authService.isAuthenticated()) {
            this.router.navigate(['/main']);
        }
    }

    public login(): void {
        this.isValid() && this.authService.login(this.username, this.password).subscribe(result => {
            if (result) {
                this.router.navigate(['/admin/admin-view']);
                this.authFailed = true;
            } else {
                this.authFailed = true;
            }
        });
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
