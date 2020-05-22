import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {Component, Inject} from "@angular/core";
import {faMapMarkedAlt} from '@fortawesome/free-solid-svg-icons';
import {Moment} from "moment";
import {MouseEvent} from '@agm/core';
import {City} from "../City";

@Component({
    selector: 'set-route-dialog',
    templateUrl: 'map.dialog.html',
})
export class MapDialog {
    iconRoute = faMapMarkedAlt;
    deadline: Moment;
    waypoints: any[];

    constructor(@Inject(MAT_DIALOG_DATA) public cities: City[]) {
        this.waypoints = cities.map(city => {
            return {lat: city.lat, lng: city.lng};
        });
    }

    clickedMarker(label: string, index: number) {
        console.log(`clicked the marker: ${label || index}`)
    }

    markerDragEnd(m: City, $event: MouseEvent) {
        console.log('dragEnd', m, $event);
    }
}