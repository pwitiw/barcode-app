import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { OrderModel } from 'src/app/models/OrderModel';
import { OrdersService } from 'src/app/services/orders/orders.service';
import { OrdersResponse } from 'src/app/models/OrdersResponse';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html'
})
export class OrdersComponent implements OnInit {
  orderList: Observable<OrdersResponse>;

  constructor(private orderService: OrdersService) { }

  ngOnInit() {
    this.updateOrderList();
  }

  private updateOrderList() {
    this.orderList = this.orderService.getOrdersList();
  }
}
