import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { OrderModel } from 'src/app/models/OrderModel';

@Component({
  selector: 'order-row',
  templateUrl: './order.component.html'
})
export class OrderComponent implements OnInit {

  @Input() order: OrderModel;
  @Output() orderDetails = new EventEmitter<OrderModel>();

  constructor() { }

  ngOnInit() {
  }

  showDetails() {
    this.orderDetails.emit(this.order);
  }
}
