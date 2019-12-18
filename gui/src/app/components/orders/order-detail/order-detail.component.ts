import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
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
import {StageService} from '../stage.service';
import {Front} from "../types/Front";

@Component({
    selector: 'order-detail',
    templateUrl: './order-detail.component.html'
})
export class OrderDetailComponent implements OnInit, OnChanges {
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


    constructor(protected stageService: StageService) {
    }

    ngOnInit() {
    }

    ngOnChanges(changes: SimpleChanges): void {
    }

    getBorderColor(front: Front): string {
        const processedQuantity = this.quantityProcessedAtCurrentstage(front);
        if (processedQuantity == front.quantity) {
            return "complete";
        } else if (processedQuantity == 0) {
            return "not-started";
        } else {
            return "incomplete";
        }
    }

    getQuantity(front: Front) {
        if (front.stage == StageService.INIT.value) {
            return "";
        }
        const processedQuantity = this.quantityProcessedAtCurrentstage(front);
        return processedQuantity + "/" + front.quantity;
    }

    private quantityProcessedAtCurrentstage(front: Front): number {
        return front.processings.filter(p => front.stage === p.stage).length;
    }
}
