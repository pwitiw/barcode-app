import { Component, OnInit } from '@angular/core';
import { LoginModel } from 'src/app/models/LoginModel';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';
import { AuthGuardService } from 'src/app/services/auth-guard/auth-guard.service';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
  private loginModel: LoginModel;
  private resp: any;

  public isWrong: boolean;

  constructor(public route: ActivatedRoute,
    private authService: AuthService,
    private router: Router,
    private guard: AuthGuardService) { }

  ngOnInit() {
    this.loginModel = new LoginModel;
    this.isWrong = false;
  }

  public login() {
    this.getAuthResponse()
        .then(() => {
        if(this.resp.status === 200) {
          this.guard.setUser(this.loginModel.username, this.resp.headers.get('Authorization'));
          this.router.navigate(['/admin/admin-view'])
        } else {
          this.guard.logout();
          this.isWrong = true;
        }
      });
  }

  private getAuthResponse() {
    return this.authService.login(this.encodeCreds())
    .toPromise()
    .then(
      (response) =>
        this.resp = response
      ).catch(err => console.log('Login attempt failed.'));
  }

  public resetFlag() {
    this.isWrong = !this.isWrong;
  }

  private encodeCreds(): LoginModel {
    let creds: LoginModel = {
      username: this.loginModel.username,
      password: this.loginModel.password
    }
    return creds;
  }

}
