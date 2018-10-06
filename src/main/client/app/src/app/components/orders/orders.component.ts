import {AfterViewInit, Component} from "@angular/core";
import {ORDERS} from "app/models/Orders-mock";
import {NgxSmartModalService} from "ngx-smart-modal";
import {Order} from "app/models/Order";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.scss']
})
export class OrdersComponent implements AfterViewInit {

  orders = ORDERS;

  constructor(public ngxSmartModalService: NgxSmartModalService) {
  }

  isNavbar() {
    return true;
  }

  setOrder(order: Order) {
    this.ngxSmartModalService.setModalData(order, 'print');
  }

  ngAfterViewInit() {

  }

}
