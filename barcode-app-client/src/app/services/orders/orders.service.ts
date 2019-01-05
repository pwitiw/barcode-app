import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class OrdersService {

  ordersUrl: string = 'http://localhost:8888/api/orders';

  constructor(private http: HttpClient) { }
  
  getOrders() {
    return this.http.get(this.ordersUrl);
  }

  getOrderDetail(id: number) {
    return this.http.get(this.ordersUrl + '/' + id);
  }

}
