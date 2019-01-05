import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';
import 'rxjs/add/operator/map';

@Injectable()
export class AuthenticationService {
    backendUrl: string = 'http://localhost:8888/';
   
    constructor(private http: HttpClient,private router: Router) { }

    login(username: string, password: string) {
        let header = new HttpHeaders({'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*', 'Access-Control-Allow-Methods': 'GET, POST, OPTIONS', 'Access-Control-Allow-Headers': 'Origin'});        
        return this.http.post<any>(this.backendUrl + 'login', { username: username, password: password }, {headers: header})
            .map(user => {
                // login successful if there's a jwt token in the response
                if (user && user.token) {
                    // store user details and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentUser', JSON.stringify(user));
                }
                return user;
            });
    }

    logout() {
        localStorage.removeItem('currentUser');
    }
}