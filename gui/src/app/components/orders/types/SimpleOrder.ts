export interface SimpleOrder {
    id: number;
    name: string;
    lastProcessedOn: Date;
    quantity: number;
    stage: string;
    customer: string;
    route: string;
    packed: boolean;
}
