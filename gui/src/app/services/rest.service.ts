import {HttpClient, HttpResponse} from "@angular/common/http";
import {catchError, tap} from "rxjs/operators";
import {Observable, of} from "rxjs";
import {Injectable} from "@angular/core";
import {LoadingService} from "./loading.service";
import {SnackBarService} from "./snack-bar.service";

@Injectable()
export class RestService {

    constructor(private http: HttpClient,
                private loadingService: LoadingService,
                private snackBarService: SnackBarService) {
    }

    public post<T>(url: string, body: any, options?): Observable<HttpResponse<T>> {
        setTimeout(() => this.loadingService.show(), 200);
        return this.http
            .post(url, body, {observe: "response", ...options},)
            .pipe(
                tap(() => {
                    this.loadingService.hide();
                }),
                catchError(error => this.handleError(error))
            ) as Observable<HttpResponse<T>>;
    }

    public get<T>(url, options?): Observable<HttpResponse<T>> {
        setTimeout(() => this.loadingService.show(), 500);
        return this.http.get(url, {observe: 'response', ...options})
            .pipe(
                tap(() => this.loadingService.hide()),
                catchError(error => this.handleError(error))
            ) as Observable<HttpResponse<T>>;
    }

    public put<T>(url: string, body: any): Observable<HttpResponse<T>> {
        setTimeout(() => this.loadingService.show(), 500);
        return this.http.put(url, body)
            .pipe(
                tap(() => this.loadingService.hide()),
                catchError(error => this.handleError(error))
            ) as Observable<HttpResponse<T>>;
    }

    private handleError<T>(error: any): Observable<T> {
        this.loadingService.hide();
        this.snackBarService.failure("Wystąpił błąd serwera");
        console.info(error);
        return of(error);
    };
}
