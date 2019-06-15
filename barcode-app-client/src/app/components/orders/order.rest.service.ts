import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {OrderDetails} from 'src/app/components/orders/order-detail/OrderDetails';
import {RestService} from "../../services/rest.service";

@Injectable()
export class OrderRestService {
  private static readonly ORDERS_ENDPOINT = "/api/orders";

  constructor(private restService: RestService) {
  }

  public getOrdersList(): Observable<any> {
    return this.restService.get<any>(OrderRestService.ORDERS_ENDPOINT);

  }

  public getOrderDetail(barcode: number): Observable<OrderDetails> {
    return this.restService.get<OrderDetails>(OrderRestService.ORDERS_ENDPOINT + '/' + barcode)

  }
}
