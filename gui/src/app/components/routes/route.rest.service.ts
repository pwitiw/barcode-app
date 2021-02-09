import { HttpResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { RestService } from "src/app/services/rest.service";
import { DeliveryInfo } from "./route-details/DeliveryInfo";
import { RouteDetails } from "./route-details/RouteDetails";

@Injectable()
export class RouteRestService {
    private static readonly ROUTES_ENDPOINT = "/api/routes";

    constructor(private restService: RestService) {
    }

    getDeliveryInfoFor(route: string): Observable<DeliveryInfo[]> {
        return this.restService.get<DeliveryInfo[]>(RouteRestService.ROUTES_ENDPOINT + '/deliveryinfo?route=' + route)
            .pipe(map(response => response.body));
    }

    getRoutes(): Observable<RouteDetails[]> {
        return this.restService.get<RouteDetails[]>(RouteRestService.ROUTES_ENDPOINT)
            .pipe(map(response => {
                response.body.forEach(details => {
                    if (details.date) {
                        details.date = new Date(details.date as any * 1000);
                    }
                });
                return response.body;
            }));
    }

    saveRoute(routeDetails: RouteDetails): Observable<boolean> {
        return this.restService.post<boolean>(RouteRestService.ROUTES_ENDPOINT, routeDetails)
            .pipe(map(response => response.ok));
    }

    deleteRoute(id: string): Observable<HttpResponse<boolean>> {
        return this.restService.delete<boolean>(RouteRestService.ROUTES_ENDPOINT + "/" + id);
    }

    fulfillRoute(id: string): Observable<HttpResponse<boolean>> {
        return this.restService.put<boolean>(RouteRestService.ROUTES_ENDPOINT + "/" + id + "/fulfill", {});
    }

    getRouteDocument(routeDetails: RouteDetails): Observable<any> {
        return this.restService.post(RouteRestService.ROUTES_ENDPOINT + 'summary',
            routeDetails,
            { responseType: 'arraybuffer' });
    }

}