import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ORDERS} from "../../models/Orders-mock";
import {NgxSmartModalService} from '../../../../node_modules/ngx-smart-modal';
import {Order} from "../../models/Order";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.scss']
})
export class OrdersComponent implements AfterViewInit {

  orders = ORDERS;
  constructor(public ngxSmartModalService: NgxSmartModalService) { }

  isNavbar(){
    return true;
  }

  setOrder(order: Order) {
    this.ngxSmartModalService.setModalData(order, 'print');
  }

  ngAfterViewInit() {

  }

}
