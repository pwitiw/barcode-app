import {Component, OnInit, ViewChild} from '@angular/core';
import {SimpleOrder} from 'src/app/components/orders/types/SimpleOrder';
import {OrderRestService} from 'src/app/components/orders/order.rest.service';
import {SearchCriteria} from "./types/SearchCriteria";
import {Page} from "../types/Page";
import {SnackBarService} from "../../services/snack-bar.service";
import {MatDialog} from "@angular/material/dialog";
import {OrderDetailsDialog} from "./order-detail/order-details.dialog";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {getIndex, PolishPaginator} from "../../services/polish-paginator.service";
import {StageService} from "./stage.service";

@Component({
    selector: 'app-orders',
    templateUrl: './orders.component.html'
})
export class OrdersComponent implements OnInit {
    orderColumns = ['index', 'name', 'quantity', 'lastProcessedOn', 'stage', 'customer', 'route', 'completed'];
    criteria: SearchCriteria;
    @ViewChild("paginator", {} as any)
    paginator: MatPaginator;
    page: number;
    sizeOptions = PolishPaginator.PAGE_SIZE_OPTIONS;
    size;
    totalElements: number;
    orders: SimpleOrder[];
    getIndex = (i) => getIndex(i, this.page, this.size);

    constructor(private orderRestService: OrderRestService,
                private snackBarService: SnackBarService,
                public stageService: StageService,
                private dialog: MatDialog) {
        this.criteria = {};
        this.page = 0;
        this.size = this.sizeOptions[0];
        this.totalElements = 0;
    }

    ngOnInit(): void {
        this.getOrders()
    }

    handleSearchClicked(criteria: SearchCriteria): void {
        const displayOrderFoundSnackBar = () => {
            this.snackBarService.success("Znaleziono " + this.totalElements + " wyników");
        };
        this.criteria = criteria;
        this.paginator.firstPage();
        this.getOrders(displayOrderFoundSnackBar);
    }

    getOrders(consumer?: () => void): void {
        const page: Page<SearchCriteria> = {
            content: this.criteria,
            page: this.page,
            size: this.size,
            totalElements: this.totalElements
        };
        this.orderRestService.getOrders(page).subscribe(result => {
            if (result) {
                this.page = result.number;
                this.totalElements = result.totalElements;
                this.orders = result.content;
                consumer && consumer();
            }
        });
    }

    handleFetchClicked(): void {
        this.orderRestService.synchronize().subscribe(result => {
            if (result != null) {
                this.snackBarService.success("Pobrano " + result + " zamowień");
            } else {
                this.snackBarService.failure("Pobieranie nie powiodło się");
            }
        });
    }

    handleShowDetails(order: SimpleOrder): void {
        this.orderRestService.getOrderDetails(order.id).subscribe(order => {
            if (order != null) {
                this.dialog.open(OrderDetailsDialog, {
                    minWidth: '90%',
                    data: order
                });
            }
        });
    }

    paginationChanged($event: PageEvent): void {
        this.page = $event.pageIndex;
        this.size = $event.pageSize;
        this.getOrders();
    }

    statusChanged(order: SimpleOrder) {
        this.orderRestService.changeStatus(order.id)
            .subscribe(result => {
                if (result) {
                    this.snackBarService.success("Zmieniono status");
                } else {
                    order.completed = !order.completed;
                    this.snackBarService.failure("Zmiana statusu zamówienia nie powiodła się");
                }
            });
    }
}
