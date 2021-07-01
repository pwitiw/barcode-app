import {Component, OnInit} from '@angular/core';
import {RestService} from "../../../services/rest.service";

interface CustomerStatistics {
    name: string;
    quantity: number;
    meters: number;
}

interface OrderStatistics {
    type: string;
    orders: number;
    complaints: number;
}

interface StageStatistics {
    period: string,
    stage: string,
    hourlyStatisticsDto:HourlyStatisticsDto[]
}

interface HourlyStatisticsDto {
    hour: number,
    meters: number
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
    stages: StageStatistics;

    constructor(private restService: RestService) {
        this.customerColumns = ['index', 'name', 'quantity', 'meters'];
        this.customers = [
            {name: "Kowalski Andrzej", quantity: 12, meters: 232.0},
            {name: "Paweł Jankowsczykiewicz", quantity: 12, meters: 232.0},
            {name: "Paź", quantity: 12, meters: 232.0},
        ];
        this.orderColumns = ['type', 'orders', 'complaints'];
    }

    ngOnInit() {
        this.loadOrderStatistics();
        this.loadStageStatistics();
    }

    loadOrderStatistics(): void {
        this.restService.get<any>(`/api/statistics/orders`)
            .subscribe(response => {
                const result = response.body.periods;
                if (result) {
                    result.map(r => r.type = StatisticsComponent.translate(r.type));
                    this.orders = result;
                }
            });
    }

    display(stages){
        return JSON.stringify(stages);
    }

    loadStageStatistics(): void {
        this.restService.get<any>(`/api/statistics/stage`)
            .subscribe(response => {
                const result = response.body;
                if (result) {
                    this.stages = result;
                }
            })
    }

    private static translate(type: string): string {
        if (type == 'TODAY')
            return 'Dzisiaj';
        if (type == 'WEEK')
            return 'Tydzień';
        if (type == 'MONTH')
            return 'Miesiąc';
        if (type == 'QUARTER')
            return 'Kwartał';
        if (type == 'YEAR')
            return 'Rok';
    }
}