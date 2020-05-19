import {Address} from "./Address";
import {forkJoin, Observable, of} from "rxjs";
import {Injectable} from "@angular/core";
import {AgmGeocoder, GeocoderResult} from "@agm/core";
import {catchError, filter, map} from "rxjs/operators";
import {LocalStorageService} from "../../../services/local-storage.service";
import {City} from "./City";

@Injectable()
export class GoogleApi {
    constructor(private geocoder: AgmGeocoder,
                private localStorage: LocalStorageService) {
    }

    getDetails(addresses: Address[]): Observable<City[]> {
        const $addresses = addresses.map((address) => this.getCity(address));
        return forkJoin($addresses).pipe(filter(city => city != null));
    }

    private getCity(address: Address): Observable<City> {
        const cached = this.localStorage.getCity(address);
        return cached ?
            of(cached) :
            this.callGoogleApi(address);
    }

    private callGoogleApi(address: Address) {
        return this.geocoder.geocode({address: address.name})
            .pipe(
                map((results: GeocoderResult[]) => {
                    const {lat, lng} = results[0].geometry.location;
                    const name = results[0].formatted_address;
                    const city: City = { name, lat: lat(), lng: lng()};
                    this.localStorage.storeCity(address, city);
                    return city
                }),
                catchError((response) => {
                    console.log(response);
                    this.localStorage.storeCity(address, null);
                    return of(null);
                })
            );
    }
}
