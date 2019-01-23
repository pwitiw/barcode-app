import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { OrderModel } from 'src/app/models/OrderModel';
import { OrdersService } from 'src/app/services/orders/orders.service';
import { OrdersResponse } from 'src/app/models/OrdersResponse';
import { OrderDetailModel } from 'src/app/models/OrderDetailModel';
import { ComponentModel } from 'src/app/models/ComponentModel';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html'
})
export class OrdersComponent implements OnInit {
  orderList: Observable<OrdersResponse>;
  orderDetail: Observable<OrderDetailModel>
  componentDetail: ComponentModel;
  barcodeUrl: String;

  constructor(private orderService: OrdersService, private modalService: NgbModal, private sanitizer: DomSanitizer) { }

  ngOnInit() {
    this.updateOrderList();
  }

  private updateOrderList() {
    this.orderList = this.orderService.getOrdersList();
  }

  handleOrderDetails(order: OrderModel) {
    this.componentDetail = null;
    this.orderDetail = this.orderService.getOrderDetail(order.barcode);
  }

  handleComponentDetails(component: ComponentModel) {
    this.componentDetail = component;
  }

  async showBarcodeModal(content) {
      let barcode = await this.getCurrentBarcode();
      this.barcodeUrl = URL.createObjectURL(barcode);
      this.modalService.open(content);
  }

  private getCurrentBarcode(): Promise<Blob> {
    return this.orderDetail.toPromise()
    .then(
      (data) => {
        return data.barcode
      })
    .then(
      (barcode) => {
        return this.orderService.getBarcodePdf(+barcode).toPromise() as Promise<Blob>
    })
    .catch(err => {
      console.log('Missing order detail data.')
      return null;
    });  
    
  }
}