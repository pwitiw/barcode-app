import {Component, OnInit} from '@angular/core';
import {PageEvent} from "@angular/material/paginator";
import {RestService} from "../../services/rest.service";
import {getIndex, PolishPaginator} from "../../services/polish-paginator.service";

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
})
export class HomeComponent implements OnInit {
    remindersColumns = ['index', 'name', 'customer', 'deadline'];
    page: number;
    sizeOptions = PolishPaginator.PAGE_SIZE_OPTIONS;
    size: number;
    totalElements: number;
    reminders: OrderReminder[];
    getIndex = (i) => getIndex(i, this.page, this.size);

    constructor(private restService: RestService) {
        this.page = 0;
        this.size = this.sizeOptions[0];
        this.totalElements = 0;
    }

    ngOnInit() {
        this.loadDeadlines();
    }

    paginationChanged($event: PageEvent): void {
        this.page = $event.pageIndex;
        this.size = $event.pageSize;
        this.loadDeadlines();
    }

    loadDeadlines(): void {
        this.restService.get<any>(`/api/orders/reminders?page=${this.page}&size=${this.size}`)
            .subscribe(response => {
                const result = response.body;
                if (result) {
                    this.page = result.number;
                    this.totalElements = result.totalElements;
                    this.reminders = result.content;
                }
            });
    }
}

interface OrderReminder {
    name: string;
    customer: string;
    deadline: number
}
