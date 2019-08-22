import {NgModule} from "@angular/core";
import {OrderRow} from './order-row/order-row.component';
import {OrdersComponent} from './orders.component';
import {OrderRestService} from './order.rest.service';
import {OrderDetailComponent} from './order-detail/order-detail.component';
import {SearchCriteriaComponent} from './search-criteria/search-criteria.component';
import {CommonModule} from "@angular/common";
import {CommonsModule} from "../commons/commons.module";
import {NgxPaginationModule} from "ngx-pagination";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    OrderRow,
    OrdersComponent,
    OrderDetailComponent,
    SearchCriteriaComponent
  ],
  imports: [
    CommonModule,
    CommonsModule,
    NgxPaginationModule,
    FormsModule
  ],
  providers: [
    OrderRestService
  ]
})
export class OrdersModule {
}
