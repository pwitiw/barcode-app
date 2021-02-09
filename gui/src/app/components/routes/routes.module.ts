import {NgModule} from "@angular/core";
import {
    MatButtonModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatTableModule
} from "@angular/material";
import {MAT_MOMENT_DATE_ADAPTER_OPTIONS} from "@angular/material-moment-adapter";
import {StageService} from "../orders/stage.service";
import {RoutesComponent} from "./routes.component";
import {CommonModule} from "@angular/common";
import {DragDropModule} from "@angular/cdk/drag-drop";
import {RestService} from "../../services/rest.service";
import {FormsModule} from "@angular/forms";
import {MapDialog} from "./compute-route/map-dialog/map.dialog";
import {AgmCoreModule} from "@agm/core";
import {GoogleApi} from "./compute-route/GoogleApi";
import {RouteComputer} from './compute-route/route-computer.service';
import {MatDialogModule} from "@angular/material/dialog";
import { RouteDetailsDialog } from "./route-details/route-details.dialog";
import { RouteRestService } from "./route.rest.service";
import { PromptDialog } from "../commons/prompt-dialog/prompt.dialog";

@NgModule({
    declarations: [
        RoutesComponent,
        MapDialog,
        RouteDetailsDialog,
    ],
    imports: [
        MatTableModule,
        MatCheckboxModule,
        MatIconModule,
        MatButtonModule,
        MatFormFieldModule,
        MatInputModule,
        MatDatepickerModule,
        CommonModule,
        DragDropModule,
        MatExpansionModule,
        FormsModule,
        AgmCoreModule.forRoot({
            apiKey: '',
            region: 'pl',
            libraries: ['places', 'drawing', 'geometry', 'geocoder']
        }),
        MatDialogModule,
        MatTableModule,
    ],
    entryComponents: [MapDialog, RouteDetailsDialog, PromptDialog],
    providers: [
        StageService,
        RestService,
        GoogleApi,
        RouteComputer,
        RouteRestService,
        {provide: MAT_MOMENT_DATE_ADAPTER_OPTIONS, useValue: {useUtc: true}},
    ]
})
export class RoutesModule {
}
