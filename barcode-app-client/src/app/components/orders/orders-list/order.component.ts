import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { SimpleOrder } from 'src/app/components/orders/SimpleOrder';

@Component({
  selector: 'order-row',
  templateUrl: './order.component.html'
})
export class OrderComponent implements OnInit {

  @Input() order: SimpleOrder;
  @Output() orderDetails = new EventEmitter<SimpleOrder>();

  constructor() { }

  ngOnInit() {
  }

  showDetails() {
    this.orderDetails.emit(this.order);
  }
}
