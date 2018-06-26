/* 3rd party libraries */
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule  } from '@angular/forms';
import { OrdersComponent } from 'app/components/orders/orders.component';
import { TableComponent } from 'app/components/table/table.component';
import { NgxSmartModalModule } from 'ngx-smart-modal';
import { SharedModule } from './shared.module';

@NgModule({
  imports: [
    SharedModule
  ],
  declarations: [
    OrdersComponent,
  ],
  exports: [
    OrdersComponent,
  ]
})
export class OrderModule { }