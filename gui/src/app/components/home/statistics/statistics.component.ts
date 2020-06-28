import {Component, OnInit} from '@angular/core';
import {RestService} from "../../../services/rest.service";

interface CustomerStatistics {
    name: string;
    quantity: number;
    meters: number;
}

interface OrderStatistics {
    period: string;
    orders: number;
    complaints: number;
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

    constructor(private restService: RestService) {
        this.customerColumns = ['index', 'name', 'quantity', 'meters'];
        this.customers = [
            {name: "Kowalski Andrzej", quantity: 12, meters: 232.0},
            {name: "Paweł Jankowsczykiewicz", quantity: 12, meters: 232.0},
            {name: "Paź", quantity: 12, meters: 232.0},
        ];
        this.orderColumns = ['period', 'orders', 'complaints'];
        this.orders = [
            {period: 'dzień', orders: 120, complaints: 0},
            {period: 'tydzień', orders: 120, complaints: 0},
            {period: 'miesiąc', orders: 120, complaints: 0},
            {period: 'kwartał', orders: 120, complaints: 0},
            {period: 'rok', orders: 120, complaints: 0},
            {period: 'suma', orders: 120, complaints: 0}
        ];
    }

    ngOnInit() {
    }

    loadOrderStatistics(): void {
        this.restService.get<OrderStatistics[]>(`/api/home/statistics/orders}`)
            .subscribe(response => {
                const result = response.body;
                if (result) {
                    this.orders = result;
                }
            });
    }
}