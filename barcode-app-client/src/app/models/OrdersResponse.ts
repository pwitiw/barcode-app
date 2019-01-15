import { OrderModel } from './OrderModel';

export class OrdersResponse {
    content: OrderModel[];
    pageable: any;
}