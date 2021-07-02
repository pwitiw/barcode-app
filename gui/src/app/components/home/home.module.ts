import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomeComponent} from './home.component';
import {StatisticsComponent} from "./statistics/statistics.component";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatTableModule} from "@angular/material/table";
import { MatFormFieldModule, MatInputModule, MatDatepickerModule } from '@angular/material';

@NgModule({
    declarations: [
        HomeComponent,
        StatisticsComponent,
    ],
    imports: [
        CommonModule,
        MatPaginatorModule,
        MatTableModule,
        MatFormFieldModule,
        MatInputModule,
        MatDatepickerModule
    ],
    providers: []
})
export class HomeModule {
}
