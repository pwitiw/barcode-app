import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {OrderDetails} from 'src/app/components/orders/order-detail/OrderDetails';
import {Front} from "../Front";

@Component({
  selector: 'component-list',
  templateUrl: './front-list.component.html'
})
export class FrontListComponent implements OnInit {

  @Input() orderDetail: OrderDetails;
  @Output() componentDetail = new EventEmitter<Front>();
  selectedComponent: number;

  constructor() {
  }

  ngOnInit() {
  }

  showComponentDetails(component: Front) {
    this.selectedComponent = component.barcode;
    this.componentDetail.emit(component);
  }
}
