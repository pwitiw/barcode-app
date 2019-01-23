import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { OrderDetailModel } from 'src/app/models/OrderDetailModel';
import { ComponentModel } from 'src/app/models/ComponentModel';

@Component({
  selector: 'component-list',
  templateUrl: './component-list.component.html'
})
export class ComponentListComponent implements OnInit {

  @Input() orderDetail: OrderDetailModel;
  @Output() componentDetail = new EventEmitter<ComponentModel>();
  selectedComponent: number;

  constructor() { }

  ngOnInit() {
  }

  showComponentDetails(component: ComponentModel) {
    this.selectedComponent = component.barcode;
    this.componentDetail.emit(component);
  }
}
