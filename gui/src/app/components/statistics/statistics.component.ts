import {Component, OnInit} from '@angular/core';

interface CustomerStatistics {
    name: string;
    quantity: number;
    meters: number;
}

interface OrderStatistics {
    month: string;
    quantity: number;
    meters: number;
}

@Component({
    selector: 'app-statistics',
    templateUrl: './statistics.component.html',
    styleUrls: ['./statistics.component.scss']
})
export class StatisticsComponent implements OnInit {

    customers: CustomerStatistics[];
    customerColumns: string[];
    orders: OrderStatistics[];
    orderColumns: string[];

    constructor() {
        this.customerColumns = ['index', 'name', 'quantity', 'meters'];
        this.customers = [
            {name: "Kowalski Andrzej", quantity: 12, meters: 232.0},
            {name: "Paweł Jankowsczykiewicz", quantity: 12, meters: 232.0},
            {name: "Paź", quantity: 12, meters: 232.0},
        ];

        this.orderColumns = ['index', 'month', 'quantity', 'meters'];
        this.orders = [
            {month: "Styczeń", quantity: 12, meters: 232.0},
            {month: "Luty", quantity: 12, meters: 232.0},
            {month: "Marzec", quantity: 12, meters: 232.0},
        ]
    }

    ngOnInit() {
    }
}