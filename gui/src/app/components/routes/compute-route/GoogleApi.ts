import {CustomerAddress} from "./CustomerAddress";
import {forkJoin, Observable, of} from "rxjs";
import {Injectable} from "@angular/core";
import {AgmGeocoder, GeocoderResult} from "@agm/core";
import {catchError, map} from "rxjs/operators";
import {LocalStorageService} from "../../../services/local-storage.service";
import {City} from "./City";

@Injectable()
export class GoogleApi {
    constructor(private geocoder: AgmGeocoder,
                private localStorage: LocalStorageService) {
    }

    getDetails(addresses: CustomerAddress[]): Observable<CustomerAddress[]> {
        const $addresses = addresses.map((address) => this.getCity(address));
        return forkJoin($addresses)
            .pipe(map(addresses => addresses.filter(address => address.city != null)));
    }

    private getCity(customerAdress: CustomerAddress): Observable<CustomerAddress> {
        const cached = this.localStorage.getCity(customerAdress);
        const city = cached ?
            of(cached) :
            this.callGoogleApi(customerAdress)

        return city.pipe(map(city => {
            customerAdress.city = city;
            return customerAdress;
        }));
    }

    private callGoogleApi(customer: CustomerAddress) {
        return this.geocoder.geocode({address: customer.address})
            .pipe(
                map((results: GeocoderResult[]) => {
                    const {lat, lng} = results[0].geometry.location;
                    const name = results[0].formatted_address.split(",")[0];
                    const city: City = {name, lat: lat(), lng: lng()};
                    this.localStorage.storeCity(customer, city);
                    return city
                }),
                catchError((response) => {
                    this.localStorage.storeCity(customer, null);
                    return of(null);
                })
            );
    }
}
