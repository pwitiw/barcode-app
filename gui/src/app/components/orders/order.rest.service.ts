import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {OrderDetails} from 'src/app/components/orders/types/OrderDetails';
import {RestService} from "../../services/rest.service";
import {map} from "rxjs/operators";
import {Page} from "../types/Page";
import {SearchCriteria} from "./types/SearchCriteria";
import {UpdateOrder} from "./order-detail/order-details.dialog";

@Injectable()
export class OrderRestService {
    private static readonly ORDERS_ENDPOINT = "/api/orders";

    constructor(private restService: RestService) {
    }

    public getOrderDetails(id: number): Observable<OrderDetails> {
        return this.restService.get<OrderDetails>(OrderRestService.ORDERS_ENDPOINT + '/' + id)
            .pipe(map(response => response.body));

    }

    public getOrders(page: Page<SearchCriteria>): Observable<any> {
        return this.restService.post<any>(OrderRestService.ORDERS_ENDPOINT + '?page=' + page.page + '&size=' + page.size, page.content)
            .pipe(
                map(response => response.body)
            );
    }

    public changeStatus(ids: number[], completed: boolean): Observable<boolean> {
        return this.restService.put(OrderRestService.ORDERS_ENDPOINT + '/status', {ids: ids, completed: completed})
            .pipe(map(response => response == null));
    }

    synchronize(): Observable<any> {
        return this.restService.post(OrderRestService.ORDERS_ENDPOINT + '/synchronize', {})
            .pipe(map(response => response.body));
    }

    updateOrder(id: number, updateOrder: UpdateOrder): Observable<boolean> {
        return this.restService.put(OrderRestService.ORDERS_ENDPOINT + '/' + id + '/deadline', updateOrder)
            .pipe(map(response => response == null));
    }

    public getBarcodes(orderId: number): void {
        this.restService.get(OrderRestService.ORDERS_ENDPOINT + `/${orderId}/barcodes`,
        {responseType: 'arraybuffer'})
        .subscribe((response: any) => {
            if (response.body) {
                const file = new Blob([response.body], {type: 'application/pdf'});
                const fileURL = URL.createObjectURL(file);
                window.open(fileURL);
            }
        });
        }
}
