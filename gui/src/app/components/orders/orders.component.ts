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
    orderColumns = ['index', 'route', 'customer', 'name', 'quantity', 'orderedAt', 'lastProcessedOn', 'stage', 'selected', 'action'];
    criteria: SearchCriteria;
    @ViewChild("paginator", {} as any)
    paginator: MatPaginator;
    page: number;
    sizeOptions = PolishPaginator.PAGE_SIZE_OPTIONS;
    size: number;
    totalElements: number;
    orders: SimpleOrder[];
    allSelected: boolean;
    getIndex = (i: number) => getIndex(i, this.page, this.size);
    selectedIds = [];

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
                this.selectedIds = [];
                this.allSelected = false;
                this.orders.forEach(o => this.selectedIds[o.id]= false);
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

    handleGetBarcodes(): void {
        this.orderRestService.getBarcodes(this.getSelectedIds());
    }

    handleChangeToInProgress(): void {
        this.orderRestService.changeToInProgress(this.getSelectedIds()).subscribe((result) => this.showSnackBarResponse(result))
    }

    handleChangeToCompleted(): void {
        this.orderRestService.changeToCompleted(this.getSelectedIds()).subscribe((result) => this.showSnackBarResponse(result))
    }

    paginationChanged($event: PageEvent): void {
        this.page = $event.pageIndex;
        this.size = $event.pageSize;
        this.getOrders();
    }

    selectAll(allChecked:boolean): void {
        this.selectedIds.forEach((id,index)=>this.selectedIds[index] = allChecked);
    }

    select(order: SimpleOrder, selected: boolean): void {
        this.selectedIds[order.id] = selected;
        this.allSelected = this.selectedIds.filter(id=>id == false).length == 0;
    }

    noOrdersChecked(): boolean {
        return this.selectedIds.filter(val => val).length == 0;
    }

    private showSnackBarResponse(result:boolean): void {
        if (result) {
            this.snackBarService.success(`Zmieniono status dla ${this.getSelectedIds().length} zamówień `);
        } else {
            this.snackBarService.failure("Operacja nie powiodła się");
        }
    }

    private getSelectedIds(): number[] {
        return Array.from(this.selectedIds.entries())
        .filter(entry=>entry[1])
        .map(entry=> entry[0]);
    }
}
