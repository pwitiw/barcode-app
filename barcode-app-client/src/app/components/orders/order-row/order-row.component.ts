import {Component, OnInit, Input, EventEmitter, Output} from '@angular/core';
import {SimpleOrder} from 'src/app/components/orders/types/SimpleOrder';
import {faSearch} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'order-row',
  templateUrl: './order-row.component.html'
})
export class OrderRow implements OnInit {

  @Input() order: SimpleOrder;
  @Output() showDetailsClicked = new EventEmitter<SimpleOrder>();
  faSearch = faSearch;


  constructor() {
  }

  ngOnInit() {
  }

  showDetails() {
    this.showDetailsClicked.emit(this.order);
  }
}
