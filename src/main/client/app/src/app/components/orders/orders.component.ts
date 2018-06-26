import {AfterViewInit, Component, OnInit} from '@angular/core';
import {orderDetailsHeaders, orders} from "../../models/Orders-mock";
import {NgxSmartModalService} from '../../../../node_modules/ngx-smart-modal';
import {Order} from "../../models/Order";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.scss']
})

export class OrdersComponent {

  private orders: Order[] = orders;
  private headers: String[] = orderDetailsHeaders;
  
  constructor(public ngxSmartModalService: NgxSmartModalService) { }

  setOrder(order: Order) {
    this.ngxSmartModalService.setModalData(order, 'print');
  }


}
