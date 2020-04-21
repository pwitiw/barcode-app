import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomeComponent} from './home.component';
import {StatisticsComponent} from "./statistics/statistics.component";
import {MatPaginatorModule} from "@angular/material/paginator";

@NgModule({
    declarations: [
        HomeComponent,
        StatisticsComponent,
    ],
    imports: [
        CommonModule,
        MatPaginatorModule,
    ],
    providers: []
})
export class HomeModule {
}
