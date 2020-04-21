import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomeComponent} from './home.component';
import {StatisticsComponent} from "./statistics/statistics.component";


@NgModule({
    declarations: [
        HomeComponent,
        StatisticsComponent,
    ],
    imports: [
        CommonModule
    ]
})
export class HomeModule {
}
