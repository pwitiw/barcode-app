import { Component, OnInit } from '@angular/core';
import {NgxSmartModalService} from "ngx-smart-modal";

@Component({
  selector: 'app-print-modal',
  templateUrl: './print-modal.component.html',
  styleUrls: ['./print-modal.component.scss']
})
export class PrintModalComponent implements OnInit {

  constructor(public ngxSmartModalService: NgxSmartModalService) { }

  ngOnInit() {
  }

  clearModalData() {
    this.ngxSmartModalService.resetModalData('print')
  }
}
