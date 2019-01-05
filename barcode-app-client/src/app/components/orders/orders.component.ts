import {AfterViewInit, Component, OnInit} from '@angular/core';
import {orderDetailsHeaders} from "../../models/Orders-mock";
import {NgxSmartModalService} from '../../../../node_modules/ngx-smart-modal';
import {Order} from "../../models/Order";
import {OrdersService} from "../../services/orders/orders.service";
import {OrdersResponse} from "../../models/OrdersResponse";
import {OrderDetail} from "../../models/OrderDetail";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.scss']
})

export class OrdersComponent implements OnInit {

  orders: Order[];
  orderDetail: OrderDetail;
  headers: String[] = orderDetailsHeaders;
  
  constructor(public ngxSmartModalService: NgxSmartModalService, public ordersService: OrdersService) {   }

  private getOrders(): void {
    this.ordersService.getOrders().subscribe((data: OrdersResponse) => this.orders = data.content);
    console.log(this.orders);
  }

  prepareDetailForModal(id: number): void {
    this.ordersService.getOrderDetail(id).subscribe((data: OrderDetail) => {
      this.orderDetail = data;
      this.ngxSmartModalService.setModalData(this.orderDetail, 'orderDetail'); 
    });
  }

  ngOnInit() {
    this.getOrders();
  }

}
