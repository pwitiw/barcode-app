import {Component, OnInit} from '@angular/core';
import {CdkDragDrop, moveItemInArray} from "@angular/cdk/drag-drop";
import {RestService} from "../../services/rest.service";
import {SnackBarService} from "../../services/snack-bar.service";
import {DeliveryInfo, DeliveryInfoView, Order} from "./DeliveryInfoView";
import {CustomerAddress} from "./compute-route/CustomerAddress";
import {RouteComputer} from "./compute-route/route-computer.service";

@Component({
    selector: 'app-routes',
    templateUrl: './routes.component.html'
})
export class RoutesComponent implements OnInit {
    deliveryInfos: DeliveryInfoView[] = [];
    selectedDeliveryInfos: DeliveryInfoView[] = [];
    routeNamePdf: string;
    routes: string;

    constructor(private restService: RestService,
                private snackBarService: SnackBarService,
                private routeComputer: RouteComputer) {
    }

    ngOnInit() {
    }

    search(): void {
        this.restService.get<DeliveryInfo[]>('/api/routes?routes=' + this.routes)
            .subscribe((response: any) => {
                if (response.body) {
                    this.selectedDeliveryInfos = [];
                    this.deliveryInfos = RoutesComponent.mapToDeliveryInfoView(response.body);
                    this.snackBarService.success("Znaleziono " + this.deliveryInfos.length + " wyników");
                }
            });
    }

    private static mapToDeliveryInfoView(deliveryInfos: DeliveryInfo[]): DeliveryInfoView[] {
        return deliveryInfos
            .map(info => {
                return {
                    info: DeliveryInfo.of(info.customer, info.orders, info.paymentType, info.address, info.phoneNumber),
                    allChecked: false
                }
            });
    }

    drop(event: CdkDragDrop<string[]>): void {
        moveItemInArray(this.selectedDeliveryInfos, event.previousIndex, event.currentIndex);
    }

    changeAllOrders(isSelected: boolean, deliveryInfoView: DeliveryInfoView): void {
        deliveryInfoView.info.orders.forEach(o => isSelected ? this.addToRoute(deliveryInfoView, o) : this.removeFromRoute(deliveryInfoView, o));
    }

    generateRouteDocument() {
        this.restService.post('/api/route',
            {
                deliveryInfos: this.selectedDeliveryInfos.map(detail => detail.info),
                route: this.routeNamePdf,
            },
            {responseType: 'arraybuffer'})
            .subscribe((response: any) => {
                if (response.body) {
                    const file = new Blob([response.body], {type: 'application/pdf'});
                    const fileURL = URL.createObjectURL(file);
                    window.open(fileURL);
                }
            });
    }

    addToRoute(deliveryInfo: DeliveryInfoView, order: Order): void {
        order.isSelected = true;
        if (this.selectedDeliveryInfos.filter(info => info == deliveryInfo).length == 0) {
            this.selectedDeliveryInfos.push(deliveryInfo);
        }
        if (deliveryInfo.info.orders.filter(o => o.isSelected).length == deliveryInfo.info.orders.length) {
            deliveryInfo.allChecked = true;
        }
    }

    removeFromRoute(deliveryInfo: DeliveryInfoView, order: Order): void {
        order.isSelected = false;
        deliveryInfo.allChecked = false;
        this.selectedDeliveryInfos = this.selectedDeliveryInfos.filter(info => info.info.isIncludedInPlanning())
    }

    setRouteClicked(): void {
        if (this.selectedCustomersWithEmptyAddresses()) {
            this.notifyAboutEmptyCustomersWithAddress();
            return;
        }
        let addresses = this.selectedDeliveryInfos.map(detail => new CustomerAddress(detail.info.customer, detail.info.address));
        this.routeComputer.compute(addresses).subscribe(addresses => {
            if (addresses.length == 0) {
                this.snackBarService.failure("Wystąpił błąd podczas wyszukiwania trasy");
                return;
            } else if (addresses.length != this.selectedDeliveryInfos.length) {
                this.snackBarService.warn("Niektóre adresy nie zostały odnalezione.");
            } else {
                this.snackBarService.success("Kolejność została pomyślnie wprowadzona");
            }
            this.reorderRouteDetails(addresses);
        });
    }

    selectedCustomersWithEmptyAddresses(): boolean {
        return this.selectedDeliveryInfos.filter(deliveryInfo=> !deliveryInfo.info.address).length != 0;
    }

    notifyAboutEmptyCustomersWithAddress(): void {
        const customers = this.selectedDeliveryInfos.filter(deliveryInfo => !deliveryInfo.info.address)
        .map(deliveryInfo => deliveryInfo.info.customer)
        .reduce((allCustomers, customer)=> allCustomers + ", " + customer);
        this.snackBarService.failure(`Nie można wykonać operacji. Brak adresu dla: ${customers}`)
    }

    reorderRouteDetails(sortedAddresses: CustomerAddress[]): void {
        let orderedCustomers = sortedAddresses.map(a => a.customer);
        this.selectedDeliveryInfos = orderedCustomers.map(c => this.selectedDeliveryInfos.filter(details => details.info.customer == c)[0])
            .concat(this.selectedDeliveryInfos.filter(details => orderedCustomers.indexOf(details.info.customer) < 0));
    }
}

function testData(): DeliveryInfoView[] {
    const infos = [
        DeliveryInfo.of("Ostatek", [
            {name: 'TW501', valuation: 222, quantity: 33, isSelected: true},
        ], "FV", "ascjaiucbajkvhalkwvabjlk", "12346789"),
        DeliveryInfo.of("Kowalczyk", [
            {name: 'TW101', valuation: 222, quantity: 11, isSelected: true},
        ], "FV", "Wrocław", "789456123"),
        DeliveryInfo.of("Krawczyk", [
            {name: 'TW201', valuation: 222, quantity: 23, isSelected: true},
        ], "FV", "Lubin", "456321963"),
        DeliveryInfo.of("Ambrozy", [
            {name: 'TW301', valuation: 222, quantity: 101, isSelected: true},
        ], "FV", "Karpacz", "963852741"),
        DeliveryInfo.of("Wilczak", [
            {name: 'TW401', valuation: 222, quantity: 1, isSelected: true},
        ], "FV", "Drezno", "852147963"),
    ];
    return infos.map(info => {
        return {
            info: info,
            allChecked: false
        }
    });
}