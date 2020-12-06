import {CustomerAddress} from "./CustomerAddress";
import {combineLatest, forkJoin, Observable, of} from "rxjs";
import {Injectable} from "@angular/core";
import {AgmGeocoder, GeocoderResult} from "@agm/core";
import {catchError, map, take} from "rxjs/operators";
import {LocalStorageService} from "../../../services/local-storage.service";
import {City} from "./City";

@Injectable()
export class GoogleApi {
    constructor(private geocoder: AgmGeocoder,
                private localStorage: LocalStorageService) {
    }

    getDetails(addresses: CustomerAddress[]): Observable<CustomerAddress[]> {
        const $addresses = addresses.map((address) => this.getCity(address));
                return combineLatest($addresses).pipe(
            map(addresses=> addresses.filter(a=>a.city)),
            catchError(e=> {
            console.info(e);
         return of([]);
        }));
    }

    private getCity(customerAddress: CustomerAddress): Observable<CustomerAddress> {
        const cached = this.localStorage.getCity(customerAddress);
        const city = cached ? of(cached) : this.callGoogleApi(customerAddress);
        
        return city.pipe(
            map((c:City) => {
                return {
                    ...customerAddress,
                    city: c
                } as CustomerAddress;
            }), 
        );
    }

    private callGoogleApi(customer: CustomerAddress): Observable<City> {
        return this.geocoder.geocode({address: customer.address})
            .pipe(
                map((results: GeocoderResult[]) => {
                    const {lat, lng} = results[0].geometry.location;
                    const name = results[0].formatted_address.split(",")[0];
                    const city: City = {name, lat: lat(), lng: lng()};
                    this.localStorage.storeCity(customer, city);
                    return city;
                }),
                catchError((response) => {
                    this.localStorage.storeCity(customer, null);
                    return of(null);
                })
            );
    }
}
