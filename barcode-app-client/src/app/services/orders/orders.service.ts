import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { OrderModel } from 'src/app/models/OrderModel';
import { ApiUrls } from 'src/app/utils/ApiUrls';
import { catchError, tap, map } from 'rxjs/operators';
import { OrderDetailModel } from 'src/app/models/OrderDetailModel';
import { OrdersResponse } from 'src/app/models/OrdersResponse';

const headers = {
  'Content-Type': 'application/pdf'
};

@Injectable({
  providedIn: 'root'
})
export class OrdersService {

  constructor(private http: HttpClient) { }

public getOrdersList(): Observable<OrdersResponse> {
 return this.http.get<OrdersResponse>(ApiUrls.ORDERS_ENDPOINT)
 .pipe(
  tap(_ => console.log('Getting orders list'),
  catchError(this.handleError('getOrdersList', [])))
);
}

public getOrderDetail(barcode: number): Observable<OrderDetailModel> {
  return this.http.get<OrderDetailModel>(ApiUrls.ORDERS_ENDPOINT + '/' + barcode)
  .pipe(
    tap(_ => console.log('Getting order detal'),
    catchError(this.handleError('getOrderDetail', [])))
  );
}

public getBarcodePdf(barcode: number): Observable<Blob> {
  return this.http.get(ApiUrls.ORDERS_ENDPOINT + '/' + barcode + '/barcode', {responseType: 'blob'})
  .pipe(
    tap(_ => console.log('Getting pdf blob'),
    catchError(this.handleError('getBarcodePdf', [])))
  );
}

private handleError<T> (operation = 'operation', result?: T) {
  return (error: any): Observable<T> => {

    console.error(error);

    console.log(`${operation} failed: ${error.message}`);

    return of(result as T);
  };
}

}
