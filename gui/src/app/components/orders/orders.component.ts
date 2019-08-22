import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {SimpleOrder} from 'src/app/components/orders/types/SimpleOrder';
import {OrderRestService} from 'src/app/components/orders/order.rest.service';
import {OrderDetails} from 'src/app/components/orders/types/OrderDetails';
import {SearchCriteria} from "./types/SearchCriteria";
import {Page} from "./types/Page";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html'
})
export class OrdersComponent implements OnInit {
  static readonly MAX_ORDERS = 10;
  criteria: SearchCriteria;
  totalElements: number;
  page: number;
  size;

  orders: SimpleOrder[];
  orderDetail$: Observable<OrderDetails>;

  constructor(private orderService: OrderRestService) {

    this.size = OrdersComponent.MAX_ORDERS;
    this.page = 1;
    this.totalElements = 0;
  }

  handlePageChanged($event) {
    this.page = $event;
    this.handleSearchClicked();
  }

  ngOnInit(): void {
  }

  handleSearchClicked(criteria?: SearchCriteria): void {
    this.criteria = criteria ? criteria : this.criteria;
    const page: Page<SearchCriteria> = {
      content: this.criteria,
      page: this.page - 1,
      size: this.size,
      totalElements: this.totalElements
    };
    this.orderService.getOrders(page).subscribe(result => {
      this.totalElements = result.totalElements;
      // TODO pwitiw remove this
      this.handleShowDetails(result.content[0]);
      this.orders = result.content;
      // this.orders= result.content.concat(result.content).concat(result.content).concat(result.content).concat(result.content))
    });
  }

  handleShowDetails(order: SimpleOrder): void {
    this.orderDetail$ = this.orderService.getOrderDetails(order.id);
  }

  showPagination() {
    return this.totalElements > this.size;
  }
}
