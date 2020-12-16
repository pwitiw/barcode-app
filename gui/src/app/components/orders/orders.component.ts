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
    orderColumns = ['index', 'route', 'customer', 'name', 'quantity', 'orderedAt', 'lastProcessedOn', 'stage', 'barcode', 'completed'];
    criteria: SearchCriteria;
    @ViewChild("paginator", {} as any)
    paginator: MatPaginator;
    page: number;
    sizeOptions = PolishPaginator.PAGE_SIZE_OPTIONS;
    size;
    totalElements: number;
    orders: SimpleOrder[];
    allCompleted: boolean;
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
            this.paginator.firstPage();
        };
        this.criteria = criteria;
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
                this.allCompleted = false;
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

    handlePrintBarcodes(orderId: number): void {
        this.orderRestService.getBarcodes(orderId);
    }

    paginationChanged($event: PageEvent): void {
        this.page = $event.pageIndex;
        this.size = $event.pageSize;
        this.getOrders();
    }

    allCompletedChecked(): void {
        const completedIds = this.orders.map(o => o.id);
        this.orderRestService.changeStatus(completedIds, this.allCompleted)
            .subscribe(result => {
                if (result) {
                    this.snackBarService.success("Zakończono zamówienia");
                    this.orders.forEach(o => o.completed = this.allCompleted);
                } else {
                    this.allCompleted = !this.allCompleted;
                    this.snackBarService.failure("Operacja nie powiodła się");
                }
            });
    }

    completedChecked(order: SimpleOrder): void {
        this.orderRestService.changeStatus([order.id], order.completed)
            .subscribe(result => {
                if (result) {
                    this.snackBarService.success("Zakończono zamówienie");
                } else {
                    order.completed = !order.completed;
                    this.snackBarService.failure("Operacja nie powiodła się");
                }
            });
    }
}
