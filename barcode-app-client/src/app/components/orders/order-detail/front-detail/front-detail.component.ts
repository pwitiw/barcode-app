import {Component, OnInit, Input} from '@angular/core';
import {Front} from "../Front";

@Component({
  selector: 'component-detail',
  templateUrl: './front-detail.component.html'
})
export class FrontDetailComponent implements OnInit {

  @Input() componentDetail: Front;

  constructor() {
  }

  ngOnInit() {
  }

}
