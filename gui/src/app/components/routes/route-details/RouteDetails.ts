import {DeliveryInfo} from "./DeliveryInfo";

export interface RouteDetails {
    id?:string;
    name?:string;
    date?:Date;
    deliveryInfos: DeliveryInfo[];
}