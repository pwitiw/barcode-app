import {Component, OnInit} from '@angular/core';
import {SimpleOrder} from 'src/app/components/orders/types/SimpleOrder';
import {OrderRestService} from 'src/app/components/orders/order.rest.service';
import {SearchCriteria} from "./types/SearchCriteria";
import {Page} from "../types/Page";
import {SnackBarService} from "../../services/snack-bar.service";
import {MatDialog} from "@angular/material/dialog";
import {OrderDetailsDialog} from "./order-detail/order-details.dialog";

@Component({
    selector: 'app-orders',
    templateUrl: './orders.component.html'
})
export class OrdersComponent implements OnInit {
    static readonly MAX_ORDERS = 20;
    criteria: SearchCriteria;
    totalElements: number;
    page: number;
    selectedId: number;
    size: number;
    orders: SimpleOrder[];

    constructor(private orderService: OrderRestService,
                private snackBarService: SnackBarService,
                private dialog: MatDialog) {
        this.criteria = {};
        this.size = OrdersComponent.MAX_ORDERS;
        this.page = 1;
        this.totalElements = 0;
    }

    ngOnInit(): void {
        this.handleSearchClicked();
    }

    handlePageChanged($event) {
        this.page = $event;
        this.handleSearchClicked();
    }

    handleSearchClicked(criteria?: SearchCriteria): void {
        this.criteria = criteria ? criteria : this.criteria;
        const page: Page<SearchCriteria> = {
            content: this.criteria,
            page: this.page - 1,
            size: this.size,
            totalElements: this.totalElements
        };
        this.orderService.getOrders(page).subscribe(result => {
            if (result) {
                this.totalElements = result.totalElements;
                this.orders = result.content;
                criteria && this.snackBarService.success("Znaleziono " + this.totalElements + " wyników");
            }
        });
    }

    handleFetchClicked(): void {
        this.orderService.synchronize().subscribe(result => {
            if (result != null) {
                this.snackBarService.success("Pobrano " + result + " zamowień");
            } else {
                this.snackBarService.failure("Pobieranie nie powiodło się");
            }
        });
    }

    handleShowDetails(order: SimpleOrder): void {
        this.orderService.getOrderDetails(order.id).subscribe(order => {
            this.dialog.open(OrderDetailsDialog, {
                minWidth: '90%',
                data: order
            });
        });
    }

    showPagination() {
        return this.totalElements > this.size;
    }
}
