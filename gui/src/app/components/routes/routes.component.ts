import {Component, OnInit} from '@angular/core';
import {CdkDragDrop, moveItemInArray} from "@angular/cdk/drag-drop";
import {RestService} from "../../services/rest.service";
import {SnackBarService} from "../../services/snack-bar.service";

@Component({
    selector: 'app-routes',
    templateUrl: './routes.component.html'
})
export class RoutesComponent implements OnInit {
    customers = [];
    routeDetails: DeliveryInformation[] = [];
    routeNamePdf: string;
    routes: string;

    constructor(private restService: RestService,
                private snackBarService: SnackBarService) {
    }

    ngOnInit() {
    }

    search(): void {
        this.restService.get('/api/routes?routes=' + this.routes, {responseType: 'text'})
            .subscribe((response: any) => {
                if (response) {
                    this.routeDetails = [];
                    this.mapToDeliveryInfo(response);
                    this.snackBarService.success("Znaleziono " + this.customers.length + " wyników");
                }
            });
    }

    private mapToDeliveryInfo(response: any): void {
        this.customers = []
            .concat(JSON.parse(response))
            .map(e => DeliveryInformation.of(e.customer, e.orders, e.paymentType))
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
                    console.info(response);
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
}

export class DeliveryInformation {
    customer: string;
    orders: Order[];
    paymentType: string;

    static of(customer: string, orders: Order[], paymentType: string): DeliveryInformation {
        const deliveryInformation = new DeliveryInformation();
        deliveryInformation.customer = customer;
        deliveryInformation.orders = orders;
        deliveryInformation.paymentType = paymentType;
        return deliveryInformation;
    }

    isIncludedInPlanning(): boolean {
        return this.orders.filter(order => order.isSelected).length > 0;
    }

    displayAggregateOrders(): string {
        const displayOrder = (o: Order) => `${o.name} - ${o.quantity} szt.`;
        return this.orders.filter(o => o.isSelected).map(displayOrder).reduce((o1, o2) => o1 + "<br>" + o2);
    }

    calculatePrice(): number {
        return this.orders.filter(o => o.isSelected).map(o => o.price).reduce((o1, o2) => o1 + o2);
    }
}

interface Order {
    name: string;
    quantity: number;
    price: number;
    isSelected?: boolean;
}


