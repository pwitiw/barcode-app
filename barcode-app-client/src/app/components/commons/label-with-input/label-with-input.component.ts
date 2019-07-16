import {Component, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'label-with-input',
  templateUrl: './label-with-input.component.html',
})
export class LabelWithInputComponent implements OnInit {

  @Input() label: string;
  @Output() order: string;

  constructor() {
  }

  ngOnInit() {
  }

}
