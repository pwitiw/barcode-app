import { Component, OnInit } from '@angular/core';
import { RestService } from "../../services/rest.service";
import { SnackBarService } from "../../services/snack-bar.service";
import { RouteComputer } from "./compute-route/route-computer.service";
import { MatDialog } from '@angular/material';
import { RouteDetailsDialog } from './route-details/route-details.dialog';
import { RouteDetails } from './route-details/RouteDetails';
import { DeliveryInfo } from './route-details/DeliveryInfo';
import { RouteRestService } from './route.rest.service';
import { PromptDialog } from '../commons/prompt-dialog/prompt.dialog';

@Component({
    selector: 'app-routes',
    templateUrl: './routes.component.html'
})
export class RoutesComponent implements OnInit {
    routeColumns = ['index', 'name', 'date', 'action'];
    routes: RouteDetails[];

    constructor(private routeRestService: RouteRestService,
        private dialog: MatDialog,
        private snackBarService: SnackBarService,
    ) {
    }

    ngOnInit() {
        this.getRoutes();
    }

    getRoutes(): void {
        this.routeRestService.getRoutes().subscribe(routes => {
            routes.forEach(r => {
                r.deliveryInfos = r.deliveryInfos.map(di => DeliveryInfo.of(di.customer, di.orders, di.paymentType, di.address, di.phoneNumber))
            });
            this.routes = routes;
        });

    }

    handleNewRoute(): void {
        this.handleEditRoute({ name: "", deliveryInfos: [] });
    }

    handleEditRoute(details: RouteDetails): void {
        this.dialog.open(RouteDetailsDialog, {
            minWidth: '95%',
            height: '95%',
            data: details
        }).afterClosed().subscribe(result => {
            if (result) {
                this.getRoutes();
            }
        });
    }

    handleFulfillRoute(details: RouteDetails): void {
        this.dialog.open(PromptDialog, {
            data: "Czy chcesz oznaczyć trasę jako zakończoną?"
        }).afterClosed().subscribe(result => {
            if (result) {
                this.routeRestService.fulfillRoute(details.id).subscribe(() => {
                    this.getRoutes();
                    this.snackBarService.success("Pomyślnie zakończono trasę " + details.name);
                });
            }
        })
    }

    handleDeleteRoute(details: RouteDetails): void {
        this.dialog.open(PromptDialog, {
            data: "Czy na pewno usunąć trasę " + details.name + "?"
        }).afterClosed().subscribe(result => {
            if (result) {
                this.deleteRoute(details);
            }
        })
    }

    deleteRoute(details: RouteDetails): void {
        this.routeRestService.deleteRoute(details.id)
            .subscribe(() => {
                this.getRoutes();
                this.snackBarService.success("Pomyślnie usunięto trasę " + details.name);
            });
    }
}