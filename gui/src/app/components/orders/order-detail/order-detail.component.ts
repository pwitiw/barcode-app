import {Component, Input, OnInit} from '@angular/core';
import {OrderDetails} from 'src/app/components/orders/types/OrderDetails';
import {
    faArrowsAltH,
    faArrowsAltV,
    faBarcode,
    faCalendarAlt,
    faHighlighter,
    faIndustry,
    faInfoCircle,
    faPalette,
    faUserTie
} from '@fortawesome/free-solid-svg-icons';
import {faStackOverflow} from '@fortawesome/free-brands-svg-icons';
import {faCommentDots} from '@fortawesome/free-regular-svg-icons';
import {StageService} from "../stage.service";

@Component({
    selector: 'order-detail',
    templateUrl: './order-detail.component.html'
})
export class OrderDetailComponent implements OnInit {
    @Input() order: OrderDetails;
    iconBarcode = faBarcode;
    iconHeight = faArrowsAltV;
    iconWidth = faArrowsAltH;
    iconStage = faIndustry;
    iconQuantity = faStackOverflow;
    iconComment = faCommentDots;
    iconPalette = faPalette;
    iconCalendar = faCalendarAlt;
    iconCustomer = faUserTie;
    iconCutter = faHighlighter;
    iconName = faInfoCircle;


    constructor(protected stageService:StageService) {
    }

    ngOnInit() {
    }
}