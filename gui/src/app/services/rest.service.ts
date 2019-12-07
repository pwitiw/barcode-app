import {HttpClient, HttpResponse} from "@angular/common/http";
import {catchError, tap} from "rxjs/operators";
import {Observable, of} from "rxjs";
import {Injectable} from "@angular/core";
import {LoadingService} from "./loading.service";
import {LocalStorageService} from "./local-storage.service";

@Injectable()
export class RestService {

    constructor(private http: HttpClient,
                private loadingService: LoadingService,
                private localStorageService: LocalStorageService) {
    }

    public post<T>(url: string, body: any): Observable<HttpResponse<T>> {
        setTimeout(() => this.loadingService.show(), 500);
        return this.http
            .post(url, body, {observe: "response"})
            .pipe(
                tap(() => this.loadingService.hide()),
                catchError(error => this.handleError(error))
            ) as Observable<HttpResponse<T>>;
    }

    public get<T>(url): Observable<T> {
        setTimeout(() => this.loadingService.show(), 500);
        return this.http.get(url)
            .pipe(
                tap(() => this.loadingService.hide()),
                catchError(error => this.handleError(error))
            ) as Observable<T>;
    }

    private handleError<T>(error: any): Observable<T> {
        this.loadingService.hide();

        console.error("Rest call error: " + error.message);
        return of(error);
    };
}
