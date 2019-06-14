import {HttpClient, HttpResponse} from "@angular/common/http";
import {catchError, tap} from "rxjs/operators";
import {Observable, of} from "rxjs";
import {Injectable} from "@angular/core";

@Injectable()
export class RestService {

  private static readonly BASE_URL = "/api";

  constructor(private http: HttpClient) {
  }

  public post(url: string, body: any): Observable<HttpResponse<Object>> {
    // @ts-ignore
    return this.http.post(RestService.BASE_URL + url, body, {observe: "response"}).pipe(
      tap(_ => console.log('Authorization in progress')),
      catchError(this.handleError<string>())
    );
  }

  private handleError<T>(result?: T) {
    return (error: any): Observable<T> => {
      console.log(error.message);
      return of(result as T);
    };
  }
}
