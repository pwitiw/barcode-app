import { Component, OnInit, Input } from '@angular/core';
import { OrderDetailModel } from 'src/app/models/OrderDetailModel';
import { ComponentModel } from 'src/app/models/ComponentModel';

@Component({
  selector: 'order-detail',
  templateUrl: './order-detail.component.html'
})
export class OrderDetailComponent implements OnInit {

  @Input() orderDetail: OrderDetailModel;
  selectedComponent: ComponentModel = new ComponentModel;

  constructor() { }

  ngOnInit() {
  }

}
