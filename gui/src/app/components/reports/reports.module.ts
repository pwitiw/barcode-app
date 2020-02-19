import {NgModule} from "@angular/core";
import {MatCheckboxModule, MatTableModule} from "@angular/material";
import {MAT_MOMENT_DATE_ADAPTER_OPTIONS} from "@angular/material-moment-adapter";
import {StageService} from "../orders/stage.service";
import {ReportsComponent} from "./reports.component";

@NgModule({
    declarations: [
        ReportsComponent
    ],
    imports: [
        MatTableModule,
        MatCheckboxModule
    ],
    providers: [
        StageService,
        {provide: MAT_MOMENT_DATE_ADAPTER_OPTIONS, useValue: {useUtc: true}}
    ]
})
export class ReportsModule {
}
