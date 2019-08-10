import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthService} from "./auth.service";

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {
  }
// TODO pwitiw ogarnac tutaj tego local storage
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (localStorage.currentUser && localStorage.currentUserToken) {
      request = request.clone({
        setHeaders: {
          Authorization: `${localStorage.token}`
        }
      });
    }

    return next.handle(request);
  }
}
