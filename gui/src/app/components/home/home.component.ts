import {Component, OnInit} from '@angular/core';
import {Page} from "../types/Page";
import {PageEvent} from "@angular/material/paginator";
import {RestService} from "../../services/rest.service";

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
})
export class HomeComponent implements OnInit {
    remindersColumns = ['index', 'name', 'customer', 'deadline'];
    remindersPage: Page<OrderReminder[]>;

    constructor(private restService: RestService) {
        this.remindersPage = {
            page: 1,
            size: 10,
            totalElements: 24
        };
    }

    ngOnInit() {
        this.loadDeadlines();
    }

    paginationChanged($event: PageEvent): void {
        this.remindersPage.page = $event.pageIndex;
        this.remindersPage.size = $event.pageSize;
        this.loadDeadlines();
    }

    loadDeadlines(): void {
        this.restService.get<Page<OrderReminder[]>>(`/api/orders/reminders?page=&${this.remindersPage.page}&size=${this.remindersPage.size}`)
            .subscribe(page => {
                this.remindersPage = page;
            });
    }
}

interface OrderReminder {
    name: string;
    customer: string;
    deadline: number
}
