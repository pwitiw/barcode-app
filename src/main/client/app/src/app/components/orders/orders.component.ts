import { Component, OnInit } from '@angular/core';
import {ORDERS} from "../../models/Orders-mock";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.scss']
})
export class OrdersComponent implements OnInit {

  orders = ORDERS;
  constructor() { }

  isNavbar(){
    return true;
  }

  ngOnInit() {
  }

}
