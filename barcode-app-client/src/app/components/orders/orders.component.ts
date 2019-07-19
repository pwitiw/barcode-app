import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {SimpleOrder} from 'src/app/components/orders/types/SimpleOrder';
import {OrderRestService} from 'src/app/components/orders/order.rest.service';
import {OrderDetails} from 'src/app/components/orders/types/OrderDetails';
import {map, tap} from "rxjs/operators";
import {SearchCriteria} from "./types/SearchCriteria";
import {Page} from "./types/Page";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html'
})
export class OrdersComponent implements OnInit {
  static readonly MAX_ORDERS = 2;
  page: Page<SearchCriteria>;
  orders$: Observable<SimpleOrder[]>;
  orderDetail$: Observable<OrderDetails>;

  handlePageChanged($event) {
    this.page.page = $event;
    this.handleSearchClicked(this.page.content);
  }

  constructor(private orderService: OrderRestService) {

    this.page = {
      size: OrdersComponent.MAX_ORDERS,
      page: 0,
      totalElements: 0
    }
  }

  ngOnInit(): void {
  }

  handleSearchClicked(criteria: SearchCriteria): void {
    this.page.content = criteria;
    this.orders$ = this.orderService.getOrders(this.page)
      .pipe(
        tap(result => {
          this.page.totalElements = result.totalElements;// TODO pwitiw remove this
          this.handleShowDetails(result.content[0]);
        }),
        // map(result => result.content.concat(result.content).concat(result.content).concat(result.content).concat(result.content))
        map(result => result.content)
      );
  }

  handleShowDetails(order: SimpleOrder): void {
    this.orderDetail$ = this.orderService.getOrderDetails(order.id);
  }
}
