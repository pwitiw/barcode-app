import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ORDERS} from "../../models/Orders-mock";
import {NgxSmartModalService} from '../../../../node_modules/ngx-smart-modal';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.scss']
})
export class OrdersComponent implements AfterViewInit {

  orders = ORDERS;
  constructor(public ngxSmartModalService: NgxSmartModalService) { }

  isNavbar(){
    return true;
  }

  ngAfterViewInit() {
    const pen: Object = {
      prop1: 'test',
      prop2: true,
      prop3: [{ a: 'a', b: 'b' }, { c: 'c', d: 'd' }],
      prop4: 327652175423
    };
    this.ngxSmartModalService.setModalData(pen, 'print');
  }

}
