import {Component, Input, OnInit} from '@angular/core';
import {Order} from "../../models/Order";

@Component({
  selector: 'app-component-table',
  templateUrl: './component-table.component.html',
  styleUrls: ['./component-table.component.scss']
})
export class ComponentTableComponent implements OnInit {

  @Input() order: Order;
  constructor() { }

  ngOnInit() {
  }

}
