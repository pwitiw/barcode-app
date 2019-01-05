import { Order } from "./Order";

export class OrdersResponse {

    content: Order[];
    last: boolean;
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
    sort: object;
    first: boolean;
    numberOfElements: number;
    
  }
  