import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {SearchCriteria} from "../types/SearchCriteria";

@Component({
  selector: 'search-criteria',
  templateUrl: './search-criteria.component.html'
})
export class SearchCriteriaComponent implements OnInit {

  @Output() searchClicked = new EventEmitter<SearchCriteria>();
  completed: boolean;
  orderName: string;

  constructor() {
  }

  ngOnInit() {
  }

  onCompletedChange(event) {
    this.completed = event;
  }

  onOrderNameChange(event) {
    this.orderName = event;
  }

  search() {
    const searchParams: SearchCriteria = {
      name: this.orderName
    };
    this.searchClicked.emit(searchParams);
  }

}
