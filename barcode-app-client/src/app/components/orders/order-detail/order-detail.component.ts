import {OnInit, Input, Component} from '@angular/core';
import {OrderDetails} from 'src/app/components/orders/order-detail/OrderDetails';

@Component({
  selector: 'order-detail',
  templateUrl: './order-detail.component.html'
})
export class OrderDetailComponent implements OnInit {

  @Input() orderDetail: OrderDetails;
  selectedComponent: Component;

  constructor() {
  }

  ngOnInit() {
  }

}
