import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home.component';
import { StatisticsComponent } from "./statistics/statistics.component";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatTableModule } from "@angular/material/table";
import { MatFormFieldModule, MatIconModule, MatInputModule, MatDatepickerModule, MatButtonModule, MatSelectModule } from '@angular/material';
import { MAT_MOMENT_DATE_ADAPTER_OPTIONS, MatMomentDateModule } from "@angular/material-moment-adapter";
import { FormsModule } from '@angular/forms';

@NgModule({
    declarations: [
        HomeComponent,
        StatisticsComponent,
    ],
    imports: [
        CommonModule,
        FormsModule,
        MatButtonModule,
        MatDatepickerModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        MatMomentDateModule,
        MatPaginatorModule,
        MatSelectModule,
        MatTableModule,
    ],
    providers: [
        { provide: MAT_MOMENT_DATE_ADAPTER_OPTIONS, useValue: { useUtc: true } }
    ]
})
export class HomeModule {
}
