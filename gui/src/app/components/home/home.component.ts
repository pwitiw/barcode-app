import {Component, OnInit} from '@angular/core';
import {Page} from "../types/Page";
import {PageEvent} from "@angular/material/paginator";

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
})
export class HomeComponent implements OnInit {
    reminders: OrderReminder[];
    remindersColumns = ['index', 'name', 'customer', 'deadline'];
    remindersPage: Page<OrderReminder[]>;

    constructor() {
        this.reminders = [
            {name: "TW 201", customer: "Jan Kowalski", deadline: new Date().getUTCDate()},
            {name: "TW 301", customer: "Jan Ambroży", deadline: new Date().getUTCDate()},
            {name: "TW 401", customer: "Przemysław Grzegrzółka", deadline: new Date().getUTCDate()}
        ];
        this.remindersPage = {
            content: this.reminders,
            page: 1,
            size: 10,
            totalElements: 24
        };
    }

    ngOnInit() {
    }

    paginationChanged($event: PageEvent): void {
        this.remindersPage.page = $event.pageIndex;
        this.remindersPage.size = $event.pageSize;
    }
}

interface OrderReminder {
    name: string;
    customer: string;
    deadline: number
}
