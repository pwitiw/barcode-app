import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {SimpleOrder} from 'src/app/components/orders/types/SimpleOrder';
import {faCalendarAlt, faIndustry, faInfoCircle, faUserTie, faMapMarkedAlt, faArchive} from '@fortawesome/free-solid-svg-icons';
import {faStackOverflow} from "@fortawesome/free-brands-svg-icons";
import {StageService} from "../stage.service";


@Component({
    selector: 'order-row',
    templateUrl: './order-row.component.html'
})
export class OrderRow implements OnInit {

    @Input() order: SimpleOrder;
    @Input() isSelected: boolean;
    @Output() showDetailsClicked = new EventEmitter<SimpleOrder>();
    iconStage = faIndustry;
    iconCalendar = faCalendarAlt;
    iconName = faInfoCircle;
    iconQuantity = faStackOverflow;
    iconCustomer = faUserTie;
    iconRoute = faMapMarkedAlt;
    iconPacked = faArchive;

    constructor(public  stageService: StageService) {
    }

    ngOnInit() {
    }

    showDetails() {
        this.showDetailsClicked.emit(this.order);
    }
}
