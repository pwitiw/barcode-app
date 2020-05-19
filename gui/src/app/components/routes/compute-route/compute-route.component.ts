import {Injectable} from '@angular/core';
import {MapDialog} from "./map-dialog/map.dialog";
import {SnackBarService} from "../../../services/snack-bar.service";
import {MatDialog} from "@angular/material/dialog";
import {GoogleApi} from "./GoogleApi";
import {Address} from "./Address";
import {LoadingService} from "../../../services/loading.service";

@Injectable()
export class ComputeRoute {

    private worker: Worker;

    constructor(private snackBarService: SnackBarService,
                private dialog: MatDialog,
                private googleApi: GoogleApi) {
    }

    ngOnInit() {
    }

    compute() {
        this.googleApi.getDetails(
            [
                new Address("Oleśnica"),
                new Address("Łódź"),
                new Address("Twardogóra"),
                new Address("Wrocław"),
                new Address("Bierutów")
            ]).subscribe(cities => {
            this.initWorker();
            this.worker.postMessage(cities);
        });
        if (typeof Worker !== 'undefined') {
        } else {
            this.snackBarService.failure("Operacja nie powiodła się");
        }
    }

    private initWorker(): void {
        this.worker = new Worker('./routes.worker', {type: 'module'});
        this.worker.onmessage = (result: any) => {
            this.dialog.open(MapDialog, {
                minWidth: '90%',
                data: result.data
            });
        };
    }
}
