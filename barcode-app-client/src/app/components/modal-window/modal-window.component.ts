import { Component, OnInit } from '@angular/core';
import {NgxSmartModalService} from "ngx-smart-modal";

@Component({
  selector: 'modal-window',
  templateUrl: './modal-window.html',
  styleUrls: ['./modal-window.scss']
})
export class ModalWindowComponent implements OnInit {

  constructor(public ngxSmartModalService: NgxSmartModalService) { }

  ngOnInit() {
  }

  clearModalData() {
    this.ngxSmartModalService.resetModalData('orderDetail');
  }
}
