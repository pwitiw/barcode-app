import {Front} from "./Front";

export interface OrderDetails {
    id: number;
    extId: number;
    name: string;
    color: string;
    size: string;
    cutter: string;
    comment: string;
    route: string;
    customer: string;
    stage: string;
    orderedAt: string;
    fronts: Front[];
    completed: boolean;
    packed: boolean;
    deadline: number;
    price: number;
}


