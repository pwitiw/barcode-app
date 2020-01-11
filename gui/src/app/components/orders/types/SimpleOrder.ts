export interface SimpleOrder {
    id: number;
    name: string;
    orderedAt: Date;
    quantity: number;
    stage: string;
    customer: string;
    route: string;
}
