import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { Component, Inject, OnInit } from "@angular/core";
import { Moment } from "moment";
import { CustomerAddress } from "../CustomerAddress";

@Component({
    selector: 'set-route-dialog',
    templateUrl: 'map.dialog.html',
})
export class MapDialog implements OnInit {
    deadline: Moment;
    waypoints: any[] = [];

    constructor(@Inject(MAT_DIALOG_DATA) public addresses: CustomerAddress[],
        private dialogRef: MatDialogRef<MapDialog>) {

    }

    ngOnInit(): void {
        this.calculateWaypoints();
    }

    close(result) {
        this.dialogRef.close(result);
    }

    contentLoaded(): boolean {
        return this.waypoints.length > 0 && this.addresses.length > 0;
    }

    reverseOrder() {
        if (this.addresses.length > 2) {
            this.addresses = this.addresses.slice(0, 1).concat(this.addresses.slice(1).reverse());
            this.calculateWaypoints();
        }
    }

    calculateWaypoints() {
        this.waypoints = this.addresses.map(address => {
            return { lat: address.city.lat, lng: address.city.lng };

        });
    }
}