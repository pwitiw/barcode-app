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
    deliveryInfos: DeliveryInfoView[] = [];
    selectedDeliveryInfos: DeliveryInfoView[] = [];
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
        let addresses = this.selectedDeliveryInfos.map(detail => new CustomerAddress(detail.info.customer, detail.info.address));
        this.computeRoute.compute(addresses).subscribe(addresses => {
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

    reorderRouteDetails(sortedAddresses: CustomerAddress[]): void {
        let orderedCustomers = sortedAddresses.map(a => a.customer);
        this.selectedDeliveryInfos = orderedCustomers.map(c => this.selectedDeliveryInfos.filter(details => details.info.customer == c)[0])
            .concat(this.selectedDeliveryInfos.filter(details => orderedCustomers.indexOf(details.info.customer) < 0));
    }
}