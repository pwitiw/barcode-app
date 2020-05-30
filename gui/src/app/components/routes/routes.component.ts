import {Component, OnInit} from '@angular/core';
import {CdkDragDrop, moveItemInArray} from "@angular/cdk/drag-drop";
import {RestService} from "../../services/rest.service";
import {SnackBarService} from "../../services/snack-bar.service";
import {DeliveryInformation, Order} from "./DeliveryInformation";
import {ComputeRoute} from "./compute-route/compute-route.component";
import {CustomerAddress} from "./compute-route/CustomerAddress";

@Component({
    selector: 'app-routes',
    templateUrl: './routes.component.html'
})
export class RoutesComponent implements OnInit {
    customers: DeliveryInformation[] = [];
    routeDetails: DeliveryInformation[] = [];
    routeNamePdf: string;
    routes: string;

    constructor(private restService: RestService,
                private snackBarService: SnackBarService,
                private computeRoute: ComputeRoute) {
    }

    ngOnInit() {
    }

    search(): void {
        this.restService.get('/api/routes?routes=' + this.routes, {responseType: 'text'})
            .subscribe((response: any) => {
                if (response.body) {
                    this.routeDetails = [];
                    this.mapToDeliveryInfo(response.body);
                    this.snackBarService.success("Znaleziono " + this.customers.length + " wyników");
                }
            });
    }

    private mapToDeliveryInfo(response: any): void {
        this.customers = []
            .concat(JSON.parse(response))
            .map(e => DeliveryInformation.of(e.customer, e.orders, e.paymentType, e.address, e.phoneNumber))
    }

    drop(event: CdkDragDrop<string[]>): void {
        moveItemInArray(this.routeDetails, event.previousIndex, event.currentIndex);
    }

    changeAllOrders(isSelected: boolean, customer: DeliveryInformation): void {
        customer.orders.forEach(o => isSelected ? this.addToRoute(customer, o) : this.removeFromRoute(customer, o));
    }

    generateRouteDocument() {
        this.restService.post('/api/route',
            {
                deliveryInfos: this.routeDetails,
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

    addToRoute(customer: DeliveryInformation, order: Order): void {
        order.isSelected = true;
        if (this.routeDetails.filter(info => info == customer).length == 0) {
            this.routeDetails.push(customer);
        }
    }

    removeFromRoute(customer: DeliveryInformation, order: Order): void {
        order.isSelected = false;
        this.routeDetails = this.routeDetails.filter(info => info.isIncludedInPlanning())
    }

    setRouteClicked(): void {
        let addresses = this.routeDetails.map(detail => new CustomerAddress(detail.customer, detail.address));
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
        this.routeDetails = orderedCustomers.map(c => this.routeDetails.filter(details => details.customer == c)[0])
            .concat(this.routeDetails.filter(details => orderedCustomers.indexOf(details.customer) < 0));
    }
}

function testData(): DeliveryInformation[] {
    return [
        DeliveryInformation.of("Ostatek", [
            {name: 'TW501', price: 222, quantity: 33, isSelected: true},
        ], "FV", "ascjaiucbajkvhalkwvabjlk", "12346789"),
        DeliveryInformation.of("Kowalczyk", [
            {name: 'TW101', price: 222, quantity: 11, isSelected: true},
        ], "FV", "Wrocław", "789456123"),
        DeliveryInformation.of("Krawczyk", [
            {name: 'TW201', price: 222, quantity: 23, isSelected: true},
        ], "FV", "Lubin", "456321963"),
        DeliveryInformation.of("Ambrozy", [
            {name: 'TW301', price: 222, quantity: 101, isSelected: true},
        ], "FV", "Karpacz", "963852741"),
        DeliveryInformation.of("Wilczak", [
            {name: 'TW401', price: 222, quantity: 1, isSelected: true},
        ], "FV", "Drezno", "852147963"),
    ];
}