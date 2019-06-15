import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {SimpleOrder} from 'src/app/components/orders/SimpleOrder';
import {OrderRestService} from 'src/app/components/orders/order.rest.service';
import {OrderPage} from 'src/app/models/OrderPage';
import {OrderDetails} from 'src/app/components/orders/order-detail/OrderDetails';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {DomSanitizer} from '@angular/platform-browser';
import {Front} from "./order-detail/Front";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html'
})
export class OrdersComponent implements OnInit {
  page: OrderPage;
  orderDetail: Observable<OrderDetails>;
  componentDetail: Front;
  barcodeUrl: String;

  constructor(private orderService: OrderRestService, private modalService: NgbModal, private sanitizer: DomSanitizer) {
  }

  ngOnInit() {
    this.updateOrderList();
  }

  private updateOrderList() {
    this.orderService.getOrdersList().subscribe(result => {
      this.page = {
        orders: result.content,
        page: result.pageable.pageNumber,
        size: result.pageable.pageSize
      };
    });
  }

  handleOrderDetails(order: SimpleOrder) {
    this.componentDetail = null;
    this.orderDetail = this.orderService.getOrderDetail(order.barcode);
  }

  handleComponentDetails(front: Front) {
    this.componentDetail = front;
  }
}
