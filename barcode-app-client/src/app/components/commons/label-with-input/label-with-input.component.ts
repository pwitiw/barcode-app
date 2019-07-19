import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'label-with-input',
  templateUrl: './label-with-input.component.html',
})
export class LabelWithInputComponent implements OnInit {

  @Input() label: string;
  @Input() input;
  @Input() type?: string;
  @Output() inputChanged = new EventEmitter();

  constructor() {
  }

  ngOnInit() {
    console.log(this.input);
  }

  onChange(value) {
    this.inputChanged.emit(value);
  }

  isCheckbox(){
    return this.type  === "checkbox";
  }
}
