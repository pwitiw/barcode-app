import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from 'src/app/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
  private username: string;
  private password: string;
  private authFailed: boolean = false;

  constructor(private authService: AuthService,
              private router: Router) {
  }

  ngOnInit(): void {
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/main']);
    }
  }

  public login(): void {
    if (this.isValid()) {
      this.authService.login(this.username, this.password).subscribe(result => {
        if (result) {
          this.router.navigate(['/admin/admin-view'])
        } else {
          this.authFailed = true;
        }
      });
    }
  }

  private isEmpty(arg: string) {
    return !arg || arg.trim() === "";
  }

  private isValid(): boolean {
    return !this.isEmpty(this.username)
      && !this.isEmpty(this.password);
  }


}
