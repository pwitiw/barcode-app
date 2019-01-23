import { Component, OnInit, Input } from '@angular/core';
import { ComponentModel } from 'src/app/models/ComponentModel';

@Component({
  selector: 'component-detail',
  templateUrl: './component-detail.component.html'
})
export class ComponentDetailComponent implements OnInit {

  @Input() componentDetail: ComponentModel;
  
  constructor() { }

  ngOnInit() {
  }

}
