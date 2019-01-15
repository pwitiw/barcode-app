import { Injectable } from '@angular/core';
import { LoginModel } from 'src/app/models/LoginModel';
import { Observable, of } from 'rxjs';
import { ApiUrls } from 'src/app/utils/ApiUrls';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { catchError, tap, map } from 'rxjs/operators';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  public login(credentials: LoginModel): Observable<any> {
    return this.http.post(ApiUrls.LOGIN_ENDPOINT, credentials, {observe: "response"})
    .pipe(
      tap(_ => console.log('Authorization in progress')),
      catchError(this.handleError<string>('authorization'))
    );
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.log(`${operation} failed`);

      return of(result as T);
    };
  }
}
