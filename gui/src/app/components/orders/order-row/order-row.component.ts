import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {SimpleOrder} from 'src/app/components/orders/types/SimpleOrder';
import {faCalendarAlt, faIndustry, faInfoCircle, faSearch} from '@fortawesome/free-solid-svg-icons';
import {faStackOverflow} from "@fortawesome/free-brands-svg-icons";
import {StageService} from "../stage.service";


@Component({
    selector: 'order-row',
    templateUrl: './order-row.component.html'
})
export class OrderRow implements OnInit {

    @Input() order: SimpleOrder;
    @Output() showDetailsClicked = new EventEmitter<SimpleOrder>();
    faSearch = faSearch;
    iconStage = faIndustry;
    iconCalendar = faCalendarAlt;
    iconName = faInfoCircle;
    iconQuantity = faStackOverflow;


    constructor(public  stageService: StageService) {
    }

    ngOnInit() {
    }

    showDetails() {
        this.showDetailsClicked.emit(this.order);
    }
}
