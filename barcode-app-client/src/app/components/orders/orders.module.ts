import {NgModule} from "@angular/core";
import {OrderRow} from './order-row/order-row.component';
import {OrdersComponent} from './orders.component';
import {OrderRestService} from './order.rest.service';
import {OrderDetailComponent} from './order-detail/order-detail.component';
import {FrontDetailComponent} from './order-detail/front-detail/front-detail.component';
import {SearchCriteriaComponent} from './search-criteria/search-criteria.component';
import {CommonModule} from "@angular/common";
import {CommonsModule} from "../commons/commons.module";

@NgModule({
  declarations: [
    OrderRow,
    OrdersComponent,
    OrderDetailComponent,
    FrontDetailComponent,
    SearchCriteriaComponent
  ],
  imports: [
    CommonsModule,
    CommonModule,
  ],
  providers: [
    OrderRestService
  ]
})
export class OrdersModule {
}
