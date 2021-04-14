import { CdkDragDrop, moveItemInArray } from "@angular/cdk/drag-drop";
import { Component, Inject } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { RestService } from "src/app/services/rest.service";
import { SnackBarService } from "src/app/services/snack-bar.service";
import { CustomerAddress } from "../compute-route/CustomerAddress";
import { RouteComputer } from "../compute-route/route-computer.service";
import { RouteRestService } from "../route.rest.service";
import { DeliveryInfo, Order } from "./DeliveryInfo";
import { RouteDetails } from "./RouteDetails";

@Component({
    selector: 'route-details-dialog',
    templateUrl: 'route-details.dialog.html',
})
export class RouteDetailsDialog {
    routeNamePdf: string;
    route: string;
    deliveryInfos: DeliveryInfo[] = [];

    constructor(
        @Inject(MAT_DIALOG_DATA) public routeDetails: RouteDetails,
        private routeRestService: RouteRestService,
        private snackBarService: SnackBarService,
        private routeComputer: RouteComputer,
        private dialogRef: MatDialogRef<RouteDetailsDialog>) {
    }

    ngOnInit() {
    }

    search(): void {
        this.routeRestService.getDeliveryInfoFor(this.route)
            .subscribe((deliveryInfos: DeliveryInfo[]) => {
                if (deliveryInfos) {
                    this.deliveryInfos = this.mapToDeliveryInfoView(deliveryInfos);
                    this.snackBarService.success("Znaleziono " + this.deliveryInfos.length + " wyników");
                }
            });
    }

    private mapToDeliveryInfoView(deliveryInfos: DeliveryInfo[]): DeliveryInfo[] {
        const selectedOrderIds =
            this.routeDetails.deliveryInfos
                .map(info => info.orders)
                .reduce((previous, current) => previous.concat(current), [])
                .map(o => o.id);
        return deliveryInfos
            .map(info => {
                const orders = info.orders.filter(o => selectedOrderIds.indexOf(o.id) === -1)
                return DeliveryInfo.of(info.customer, orders, info.paymentType, info.address, info.phoneNumber)
            });
    }

    drop(event: CdkDragDrop<string[]>): void {
        moveItemInArray(this.routeDetails.deliveryInfos, event.previousIndex, event.currentIndex);
    }

    handleAddAllOrders(deliveryInfo: DeliveryInfo): void {
        deliveryInfo.orders.slice().forEach(order => this.handleAddOrder(deliveryInfo, order));
    }

    generateRouteDocument() {
        this.routeRestService.getRouteDocument(this.routeDetails)
            .subscribe((response: any) => {
                if (response.body) {
                    const file = new Blob([response.body], { type: 'application/pdf' });
                    const fileURL = URL.createObjectURL(file);
                    window.open(fileURL);
                }
            });
    }

    handleAddOrder(deliveryInfo: DeliveryInfo, order: Order): void {
        deliveryInfo.orders.splice(deliveryInfo.orders.indexOf(order), 1);
        const routeDeliveryInfo = this.routeDetails.deliveryInfos.filter(di => di.customer === deliveryInfo.customer)[0];
        if (routeDeliveryInfo != null) {
            routeDeliveryInfo.orders.push(order);
        } else {
            this.routeDetails.deliveryInfos.push(DeliveryInfo.of(deliveryInfo.customer, [order], deliveryInfo.paymentType, deliveryInfo.address, deliveryInfo.phoneNumber));
        }
    }

    notEmptyDeliveryInfos(): DeliveryInfo[] {
        return this.deliveryInfos.filter(di => di.orders.length > 0);
    }

    handleRemoveFromRoute(deliveryInfo: DeliveryInfo, order: Order): void {
        deliveryInfo.orders.splice(deliveryInfo.orders.indexOf(order), 1);
        if (deliveryInfo.orders.length == 0) {
            this.routeDetails.deliveryInfos.splice(this.routeDetails.deliveryInfos.indexOf(deliveryInfo), 1);
        }
        const di = this.deliveryInfos.filter(info => info.customer == deliveryInfo.customer)[0];
        if (di != null) {
            di.orders.push(order);
        }
    }

    setRouteClicked(): void {
        if (this.selectedCustomersWithEmptyAddresses()) {
            this.notifyAboutEmptyCustomersWithAddress();
            return;
        }
        let addresses = this.routeDetails.deliveryInfos.map(detail => new CustomerAddress(detail.customer, detail.address));
        this.routeComputer.compute(addresses).subscribe(addresses => {
            if (addresses.length == 0) {
                this.snackBarService.failure("Wystąpił błąd podczas wyszukiwania trasy");
                return;
            } else if (addresses.length != this.routeDetails.deliveryInfos.length) {
                this.snackBarService.warn("Niektóre adresy nie zostały odnalezione.");
            } else {
                this.snackBarService.success("Kolejność została pomyślnie wprowadzona");
            }
            this.reorderRouteDetails(addresses);
        });
    }

    selectedCustomersWithEmptyAddresses(): boolean {
        return this.routeDetails.deliveryInfos.filter(deliveryInfo => !deliveryInfo.address).length != 0;
    }

    notifyAboutEmptyCustomersWithAddress(): void {
        const customers = this.routeDetails.deliveryInfos.filter(deliveryInfo => !deliveryInfo.address)
            .map(deliveryInfo => deliveryInfo.customer)
            .reduce((allCustomers, customer) => allCustomers + ", " + customer);
        this.snackBarService.failure(`Nie można wykonać operacji. Brak adresu dla: ${customers}`)
    }

    reorderRouteDetails(sortedAddresses: CustomerAddress[]): void {
        let orderedCustomers = sortedAddresses.map(a => a.customer);
        this.routeDetails.deliveryInfos = orderedCustomers.map(c => this.routeDetails.deliveryInfos.filter(details => details.customer == c)[0])
            .concat(this.routeDetails.deliveryInfos.filter(details => orderedCustomers.indexOf(details.customer) < 0));
    }

    handleSaveRoute(): void {
        this.routeRestService.saveRoute(this.routeDetails).subscribe(result=>{
            if(result){
                this.snackBarService.success("Zapisano trasę");
                this.dialogRef.close(true);
            }
        });
    }
}

function testData(): DeliveryInfo[] {
    return [
        DeliveryInfo.of("Ostatek", [
            { id: 1, name: 'TW101', valuation: 222, quantity: 33, orderedAt: '12/06/2020' },
            { id: 2, name: 'TW201', valuation: 222, quantity: 11, orderedAt: '12/06/2020' },
            { id: 6, name: 'TW601', valuation: 222, quantity: 11, orderedAt: '12/06/2020' },
            { id: 7, name: 'TW701', valuation: 222, quantity: 11, orderedAt: '12/06/2020' },
        ], "FV", "ascjaiucbajkvhalkwvabjlk", "12346789"),
        DeliveryInfo.of("Kowalczyk", [
            { id: 3, name: 'TW301', valuation: 222, quantity: 11, orderedAt: '12/06/2020' },
        ], "FV", "Wrocław", "789456123"),
        DeliveryInfo.of("Krawczyk", [
            { id: 4, name: 'TW401', valuation: 222, quantity: 23, orderedAt: '12/06/2020' },
        ], "FV", "Lubin", "456321963"),
        DeliveryInfo.of("Ambrozy", [
            { id: 5, name: 'TW501', valuation: 222, quantity: 101, orderedAt: '12/06/2020' },
        ], "FV", "Karpacz", "963852741"),
        DeliveryInfo.of("Wilczak", [
            { id: 6, name: 'TW601', valuation: 222, quantity: 1, orderedAt: '12/06/2020' },
        ], "FV", "Drezno", "852147963"),
    ];
}