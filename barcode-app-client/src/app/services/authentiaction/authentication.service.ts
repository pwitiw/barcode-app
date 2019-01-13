import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Observable} from "rxjs/Observable";
import 'rxjs/add/operator/map';

@Injectable()
export class AuthenticationService {
  private static readonly AUTH_HEADER = "Authorization";
  private backendUrl: string = 'http://localhost:8888/';


  constructor(private http: HttpClient, private router: Router) {
  }

  login(username: string, password: string): Observable<Object> {
    // TODO chyba lepiej min base64encode zrobic haslo
    const body = {
      username: username,
      password: password
    };
    return this.http.post<any>(this.backendUrl + 'login', body, {observe: "response"})
      .map(response => {
        if (response) {
          // TODO w tokenie zaszyfrowana jest nazwa uzytkownika, nalezy ja odszyfrowac base64 i na stronce wyswietlac + zapisac jakims servisie trzymajacym stan
          const authHeader = response.headers.get(AuthenticationService.AUTH_HEADER);
          localStorage.setItem(AuthenticationService.AUTH_HEADER, authHeader);
        }
        return response;
      });
  }

  logout() {
    localStorage.removeItem('currentUser');
  }
}
