import {Injectable, OnDestroy} from '@angular/core';
import {MapDialog} from "./map-dialog/map.dialog";
import {SnackBarService} from "../../../services/snack-bar.service";
import {MatDialog} from "@angular/material/dialog";
import {GoogleApi} from "./GoogleApi";
import {CustomerAddress} from "./CustomerAddress";
import {LoadingService} from "../../../services/loading.service";
import {Observable, Subject} from "rxjs";

@Injectable()
export class ComputeRoute implements OnDestroy {
    private static DEPARTURE = "Twardogóra";
    private worker: Worker;
    private notify = new Subject<CustomerAddress[]>();

    constructor(private snackBarService: SnackBarService,
                private dialog: MatDialog,
                private googleApi: GoogleApi,
                private loadingService: LoadingService) {
    }

    ngOnDestroy(): void {
        this.notify.complete();
    }

    compute(addresses: CustomerAddress[]): Observable<CustomerAddress[]> {
        this.loadingService.show();
        this.googleApi.getDetails(addresses.concat(new CustomerAddress("Frontwit", ComputeRoute.DEPARTURE)))
            .subscribe(customerAddresses => {
                if (customerAddresses.length > 2) {
                    this.runWorker(customerAddresses);
                } else {
                    this.loadingService.hide();
                    this.notify.next([]);
                }
            });
        return this.notify.asObservable();
    }

    private runWorker(customerAddresses: CustomerAddress[]): void {
        if (typeof Worker == 'undefined') {
            this.snackBarService.failure("Operacja nie powiodła się");
            return;
        }
        this.worker = new Worker('./routes.worker', {type: 'module'});
        this.worker.onmessage = (result: any) => {
            this.loadingService.hide();
            const cities = result.data as CustomerAddress[];
            const indexOfDeparture = cities.findIndex(address => address.city.name == ComputeRoute.DEPARTURE);
            const sortedCities = cities.slice(indexOfDeparture).concat(cities.slice(0, indexOfDeparture));
            this.openMap(sortedCities);
        };
        this.worker.postMessage(customerAddresses);
    }

    openMap(cities: CustomerAddress[]): void {
        const dialogRef = this.dialog.open(MapDialog, {
            minWidth: '100%',
            minHeight: '100%',
            data: cities
        });

        dialogRef.afterClosed().subscribe((result) => {
            if (result) {
                this.notify.next(cities.slice(1));
            }
        });
    }
}
