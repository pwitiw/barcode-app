import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { OrderModel } from 'src/app/models/OrderModel';
import { OrdersService } from 'src/app/services/orders/orders.service';
import { OrdersResponse } from 'src/app/models/OrdersResponse';
import { OrderDetailModel } from 'src/app/models/OrderDetailModel';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html'
})
export class OrdersComponent implements OnInit {
  orderList: Observable<OrdersResponse>;
  orderDetail: Observable<OrderDetailModel>

  constructor(private orderService: OrdersService) { }

  ngOnInit() {
    this.updateOrderList();
  }

  private updateOrderList() {
    this.orderList = this.orderService.getOrdersList();
  }

  handleDetails(order: OrderModel) {
    this.orderDetail = this.orderService.getOrderDetail(order.id);
  }
}
