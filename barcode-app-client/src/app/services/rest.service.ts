import {HttpClient, HttpResponse} from "@angular/common/http";
import {catchError} from "rxjs/operators";
import {Observable, of} from "rxjs";
import {Injectable} from "@angular/core";

@Injectable()
export class RestService {

  constructor(private http: HttpClient) {
  }

  public post<T>(url: string, body: any): Observable<HttpResponse<T>> {
    return this.http.post(url, body, {observe: "response"}).pipe(
      catchError(this.handleError)
    ) as Observable<HttpResponse<T>>;
  }

  public get<T>(url): Observable<T> {
    return this.http.get(url).pipe(
      catchError(this.handleError)
    ) as Observable<T>;
  }

  private handleError<T>(error: any): Observable<T> {
    console.error("Rest call error: " + error.message);
    return of(error);
  };
}
