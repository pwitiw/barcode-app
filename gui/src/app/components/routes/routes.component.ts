import {Component, OnInit} from '@angular/core';
import {CdkDragDrop, moveItemInArray} from "@angular/cdk/drag-drop";
import {RestService} from "../../services/rest.service";

@Component({
    selector: 'app-routes',
    templateUrl: './routes.component.html'
})
export class RoutesComponent implements OnInit {
    customers = ELEMENT_DATA;
    routeDetails: DeliveryInformation[] = [];
    routeInfo: RouteInformation;
    routes: string;

    constructor(private restService: RestService) {
    }

    ngOnInit() {
    }

    search(): void {
        console.log(this.routes);
        // this.restService.get("/routes/search",
        //     {routes: [routes]})
        //     .subscribe(result => {
        //         result;
        //     });
        // return this.routeDetails;
    }


    drop(event: CdkDragDrop<string[]>): void {
        moveItemInArray(this.routeDetails, event.previousIndex, event.currentIndex);
    }

    changeAllOrders(isSelected: boolean, customer: DeliveryInformation): void {
        customer.orders.forEach(o => isSelected ? this.addToRoute(customer, o) : this.removeFromRoute(customer, o));
    }

    generateRouteDocument() {
        const mediaType = 'application/pdf';
        const url = '/api/route';
        this.restService.post(url,
            {
                deliveryInfos: this.routeDetails,
                route: "tw-wroclaw"
            },
            {responseType: 'arraybuffer'})
            .subscribe((response: any) => {
                const file = new Blob([response.body], {type: mediaType});
                const fileURL = URL.createObjectURL(file);
                window.open(fileURL);
                console.info(response);
            });
    }


    getSelectedCustomers(): DeliveryInformation[] {
        return this.customers.filter(deliveryInfo => deliveryInfo.isIncludedInPlanning());
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
    isSelected: boolean;
}

interface RouteInformation {
    deliveryInfos: DeliveryInformation[];
    route: string;
}

const ELEMENT_DATA: DeliveryInformation[] = [
    DeliveryInformation.of(
        "Jan Gaj",
        [
            {name: 'TW 201', quantity: 15, price: 1000, isSelected: false},
            {name: '69', quantity: 20, price: 2000, isSelected: false}
        ],
        "FV"),
    DeliveryInformation.of(
        "Bartłomiej Szczepańczykiewicz",
        [
            {name: '69', quantity: 20, price: 2000, isSelected: false},
            {name: 'TW 1', quantity: 3, price: 300, isSelected: false},
            {name: '905', quantity: 5, price: 575, isSelected: false}
        ],
        "KP"),
    DeliveryInformation.of(
        "Patryk Wilk",
        [
            {name: '137', quantity: 15, price: 500, isSelected: false},
            {name: 'TW 1', quantity: 3, price: 300, isSelected: false},
        ],
        "KP"),
    DeliveryInformation.of(
        "Przemysław Szafraniec",
        [
            {name: 'A1', quantity: 16, price: 580, isSelected: true},
            {name: 'A3', quantity: 18, price: 459, isSelected: false},
            {name: 'A6', quantity: 20, price: 1450, isSelected: false},
            {name: 'A8', quantity: 50, price: 470, isSelected: false},
            {name: 'A9', quantity: 70, price: 2000, isSelected: false},
            {name: 'TW 1', quantity: 3, price: 300, isSelected: false},
        ],
        "KP")
];

const RouteInfo: RouteInformation = {
    deliveryInfos: ELEMENT_DATA,
    route: "Olesnica-Wroclaw"

};



