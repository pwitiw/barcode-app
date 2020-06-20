import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ChangeDetectorRef, Component, Inject} from "@angular/core";
import {Moment} from "moment";
import {CustomerAddress} from "../CustomerAddress";

@Component({
    selector: 'set-route-dialog',
    templateUrl: 'map.dialog.html',
})
export class MapDialog {
    deadline: Moment;
    waypoints: any[];

    constructor(@Inject(MAT_DIALOG_DATA) public addresses: CustomerAddress[],
                private dialogRef: MatDialogRef<MapDialog>) {
        this.waypoints = addresses.map(address => {
            return {lat: address.city.lat, lng: address.city.lng};
        });
    }

    close(result) {
        this.dialogRef.close(result);
    }
}