import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {SimpleOrder} from 'src/app/components/orders/types/SimpleOrder';
import {faCalendarAlt, faIndustry, faInfoCircle, faSearch} from '@fortawesome/free-solid-svg-icons';
import {faStackOverflow} from "@fortawesome/free-brands-svg-icons";
import {stageMapper} from "../types/Stage"


@Component({
    selector: 'order-row',
    templateUrl: './order-row.component.html'
})
export class OrderRow implements OnInit {

    @Input() order: SimpleOrder;
    @Output() showDetailsClicked = new EventEmitter<SimpleOrder>();
    stageMapper = stageMapper;
    faSearch = faSearch;
    iconStage = faIndustry;
    iconCalendar = faCalendarAlt;
    iconName = faInfoCircle;
    iconQuantity = faStackOverflow;



    constructor() {
    }

    ngOnInit() {
    }

    showDetails() {
        this.showDetailsClicked.emit(this.order);
    }
}
