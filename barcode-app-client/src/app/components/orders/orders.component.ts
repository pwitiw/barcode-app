import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {SimpleOrder} from 'src/app/components/orders/SimpleOrder';
import {OrderRestService} from 'src/app/components/orders/order.rest.service';
import {OrderDetails} from 'src/app/components/orders/order-detail/OrderDetails';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {DomSanitizer} from '@angular/platform-browser';
import {map, tap} from "rxjs/operators";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html'
})
export class OrdersComponent implements OnInit {
  pageSize: number;
  pageNumber: number;
  orders$: Observable<SimpleOrder[]>;
  orderDetail$: Observable<OrderDetails>
  barcodeUrl: String;

  constructor(private orderService: OrderRestService, private modalService: NgbModal, private sanitizer: DomSanitizer) {
  }

  ngOnInit() {
    this.updateOrderList();
  }

  private updateOrderList() {
    this.orders$ = this.orderService.getOrdersList()
      .pipe(
        tap(result => {
          this.pageNumber = result.pageable.pageNumber;
          this.pageSize = result.pageable.pageSize;
          // TODO pwitiw remove this
          this.handleShowDetails(result.content[0]);
        }),
        map(result => result.content.concat(result.content).concat(result.content).concat(result.content).concat(result.content))
      );
  }

  handleShowDetails(order: SimpleOrder) {
    this.orderDetail$ = this.orderService.getOrderDetail(order.id);
  }
}
