import {Component, Input, OnInit} from "@angular/core";

@Component({
  selector: 'label-with-text',
  templateUrl: './label-with-text.component.html'
})
export class LabelWithTextComponent implements OnInit {
  @Input() label: string;
  @Input()   text: string;


  ngOnInit(): void {
  }

}
