import { ComponentModel } from './ComponentModel';

export class OrderDetailModel {
    barcode: string
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
    components: ComponentModel[];
}