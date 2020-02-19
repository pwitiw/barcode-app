import {NgModule} from "@angular/core";
import {OrderRow} from './order-row/order-row.component';
import {OrdersComponent} from './orders.component';
import {OrderRestService} from './order.rest.service';
import {OrderDetailComponent} from './order-detail/order-detail.component';
import {SearchCriteriaComponent} from './search-criteria/search-criteria.component';
import {CommonModule} from "@angular/common";
import {CommonsModule} from "../commons/commons.module";
import {NgxPaginationModule} from "ngx-pagination";
import {
    MatButtonModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatDividerModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatOptionModule,
    MatSelectModule
} from "@angular/material";
import {StageService} from "./stage.service";
import {FormsModule} from "@angular/forms";
import {MAT_MOMENT_DATE_ADAPTER_OPTIONS, MatMomentDateModule} from "@angular/material-moment-adapter";

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
        MatFormFieldModule,
        MatOptionModule,
        MatSelectModule,
        MatInputModule,
        MatCheckboxModule,
        MatDividerModule,
        MatButtonModule,
        MatIconModule,
        FormsModule,
        MatDatepickerModule,
        MatMomentDateModule
    ],
    providers: [
        OrderRestService,
        StageService,
        {provide: MAT_MOMENT_DATE_ADAPTER_OPTIONS, useValue: {useUtc: true}}
    ]
})
export class OrdersModule {
}
