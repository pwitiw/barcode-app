import {Component, OnInit} from '@angular/core';
import {CdkDragDrop, moveItemInArray} from "@angular/cdk/drag-drop";
import {RestService} from "../../services/rest.service";
import {SnackBarService} from "../../services/snack-bar.service";
import {DeliveryInfo, DeliveryInfoView, Order} from "./DeliveryInfoView";
import {ComputeRoute} from "./compute-route/compute-route.component";
import {CustomerAddress} from "./compute-route/CustomerAddress";

@Component({
    selector: 'app-routes',
    templateUrl: './routes.component.html'
})
export class RoutesComponent implements OnInit {
    customers: DeliveryInfoView[] = [];
    routeDetails: DeliveryInfoView[] = [];
    routeNamePdf: string;
    routes: string;

    constructor(private restService: RestService,
                private snackBarService: SnackBarService,
                private computeRoute: ComputeRoute) {
    }

    ngOnInit() {
    }

    search(): void {
        this.restService.get<DeliveryInfo[]>('/api/routes?routes=' + this.routes)
            .subscribe((response: any) => {
                if (response.body) {
                    this.routeDetails = [];
                    this.customers = RoutesComponent.mapToDeliveryInfoView(response.body);
                    this.snackBarService.success("Znaleziono " + this.customers.length + " wyników");
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
        moveItemInArray(this.routeDetails, event.previousIndex, event.currentIndex);
    }

    changeAllOrders(isSelected: boolean, deliveryInfoView: DeliveryInfoView): void {
        deliveryInfoView.info.orders.forEach(o => isSelected ? this.addToRoute(deliveryInfoView, o) : this.removeFromRoute(deliveryInfoView, o));
    }

    generateRouteDocument() {
        this.restService.post('/api/route',
            {
                deliveryInfos: this.routeDetails.map(detail => detail.info),
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

    addToRoute(customer: DeliveryInfoView, order: Order): void {
        order.isSelected = true;
        if (this.routeDetails.filter(info => info == customer).length == 0) {
            this.routeDetails.push(customer);
        }
        if (customer.info.orders.filter(o => o.isSelected).length == customer.info.orders.length) {
            customer.allChecked = true;
        }
    }

    removeFromRoute(customer: DeliveryInfoView, order: Order): void {
        order.isSelected = false;
        customer.allChecked = false;
        this.routeDetails = this.routeDetails.filter(info => info.info.isIncludedInPlanning())
    }

    setRouteClicked(): void {
        let addresses = this.routeDetails.map(detail => new CustomerAddress(detail.info.customer, detail.info.address));
        this.computeRoute.compute(addresses).subscribe(addresses => {
            if (addresses.length == 0) {
                this.snackBarService.failure("Wystąpił błąd podczas wyszukiwania trasy");
                return;
            } else if (addresses.length != this.routeDetails.length) {
                this.snackBarService.warn("Niektóre adresy nie zostały odnalezione.");
            } else {
                this.snackBarService.success("Kolejność została pomyślnie wprowadzona");
            }
            this.reorderRouteDetails(addresses);
        });
    }

    reorderRouteDetails(sortedAddresses: CustomerAddress[]): void {
        let orderedCustomers = sortedAddresses.map(a => a.customer);
        this.routeDetails = orderedCustomers.map(c => this.routeDetails.filter(details => details.info.customer == c)[0])
            .concat(this.routeDetails.filter(details => orderedCustomers.indexOf(details.info.customer) < 0));
    }

    static isChecked(customer: DeliveryInfoView): boolean {
        return customer.info.orders.every(o => o.isSelected)
    }
}

export class RouteDetails {
    customer: string;
    orders: Order[];
    paymentType: string;
    address: string;
    phoneNumber: string;
}

// function testData(): DeliveryInfoView[] {
//     return [
//         DeliveryInfoView.info.of("Ostatek", [
//             {name: 'TW501', valuation: 222, quantity: 33, isSelected: true},
//         ], "FV", "ascjaiucbajkvhalkwvabjlk", "12346789"),
//         DeliveryInfoView.of("Kowalczyk", [
//             {name: 'TW101', valuation: 222, quantity: 11, isSelected: true},
//         ], "FV", "Wrocław", "789456123"),
//         DeliveryInfoView.of("Krawczyk", [
//             {name: 'TW201', valuation: 222, quantity: 23, isSelected: true},
//         ], "FV", "Lubin", "456321963"),
//         DeliveryInfoView.of("Ambrozy", [
//             {name: 'TW301', valuation: 222, quantity: 101, isSelected: true},
//         ], "FV", "Karpacz", "963852741"),
//         DeliveryInfoView.of("Wilczak", [
//             {name: 'TW401', valuation: 222, quantity: 1, isSelected: true},
//         ], "FV", "Drezno", "852147963"),
//     ];
// }