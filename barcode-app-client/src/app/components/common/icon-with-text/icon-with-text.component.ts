import {Component, Input, OnInit} from '@angular/core';
import {IconDefinition} from '@fortawesome/fontawesome-common-types';

@Component({
  selector: 'icon-with-text',
  templateUrl: './icon-with-text.component.html',
})
export class IconWithTextComponent implements OnInit {

  @Input() icon: IconDefinition;
  @Input() text: string;
  @Input() title?: string;

  constructor() {
  }

  ngOnInit() {
  }

}
