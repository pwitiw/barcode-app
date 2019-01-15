import { Component, OnInit, Input } from '@angular/core';
import { OrderModel } from 'src/app/models/OrderModel';

@Component({
  selector: 'order-row',
  templateUrl: './order.component.html'
})
export class OrderComponent implements OnInit {

  @Input() order: OrderModel;

  constructor() { }

  ngOnInit() {
  }

}
