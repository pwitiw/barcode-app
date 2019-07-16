import {Component, Input, OnInit} from '@angular/core';
import {IconDefinition} from '@fortawesome/fontawesome-common-types';

@Component({
  selector: 'btn',
  templateUrl: './button.component.html',
})
export class ButtonComponent implements OnInit {

  @Input() icon?: IconDefinition;
  @Input() text?: string;

  constructor() {
  }

  ngOnInit() {
  }

}
