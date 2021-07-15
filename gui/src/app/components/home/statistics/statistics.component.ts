import { Component, OnInit, Output } from '@angular/core';
import { now } from 'moment';
import { RestService } from "../../../services/rest.service";
import { StageService } from '../../orders/stage.service';
import { Stage } from '../../orders/types/Stage';

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
    hourlyStatistics: HourlyStatistics[],
    firstShift: string,
    secondShift: string
}

interface HourlyStatistics {
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
    stageColumns: string[];
    stageStatistics: StageStatistics;
    stage: string;
    stages: Stage[];
    dateForStatistics: Date;

    constructor(private restService: RestService,
        public stageService: StageService) {
        this.customerColumns = ['index', 'name', 'quantity', 'meters'];
        this.customers = [
            { name: "Kowalski Andrzej", quantity: 12, meters: 232.0 },
            { name: "Paweł Jankowsczykiewicz", quantity: 12, meters: 232.0 },
            { name: "Paź", quantity: 12, meters: 232.0 },
        ];
        this.orderColumns = ['type', 'orders', 'complaints'];
        this.stageColumns = ['hour', 'meters'];
        this.stages = stageService.getStages();
    }

    ngOnInit() {
        this.loadOrderStatistics();
        this.dateForStatistics = new Date(now());
        this.stage = StageService.PACKING.value;
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

    loadStageStatistics(): void {
        if (!this.dateForStatistics) {
            return;
        }
        const date = this.dateForStatistics.getTime ? this.dateForStatistics.getTime() : this.dateForStatistics;
        const stage = this.stage ? this.stage : StageService.PACKING.value;
        this.restService.get<any>(`/api/statistics/stage?date=` + date + `&stage=` + stage)
            .subscribe(response => {
                const result = response.body;
                if (result) {
                    this.stageStatistics = result;
                }
            })
    }

    roundMeters(meters: number): number {
        return Number(meters.toFixed(2));
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