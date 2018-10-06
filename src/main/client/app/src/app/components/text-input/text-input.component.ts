import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-text-input',
  inputs: ['label', 'placeholder', 'value'],
  templateUrl: './text-input.component.html',
  styleUrls: ['./text-input.component.scss']
})
export class TextInputComponent implements OnInit {

  label: string;
  placeholder: string;
  value: string;

  constructor() {
  }

  ngOnInit() {
  }

}
