import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {Component, Inject} from "@angular/core";
import {OrderDetails} from "../types/OrderDetails";
import {faStackOverflow} from "@fortawesome/free-brands-svg-icons";
import {faCommentDots} from "@fortawesome/free-regular-svg-icons";
import {
    faArrowsAltH,
    faArrowsAltV,
    faBarcode,
    faCalendarAlt,
    faHighlighter,
    faIndustry,
    faInfoCircle,
    faMapMarkedAlt,
    faPalette,
    faUserTie
} from '@fortawesome/free-solid-svg-icons';
import {StageService} from "../stage.service";
import {OrderRestService} from "../order.rest.service";
import {SnackBarService} from "../../../services/snack-bar.service";
import {Front} from "../types/Front";
import * as moment from "moment";
import {Moment} from "moment";

@Component({
    selector: 'order-details-dialog',
    templateUrl: 'order-details.dialog.html',
})
export class OrderDetailsDialog {
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
    iconRoute = faMapMarkedAlt;
    deadline: Moment;

    constructor(
        @Inject(MAT_DIALOG_DATA) public order: OrderDetails,
        public stageService: StageService,
        private orderRestService: OrderRestService,
        private snackBarService: SnackBarService) {
    }

    ngOnInit() {
        this.deadline = moment(this.order.deadline);
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

    displayNameWithOptionalType(name: string, type: string): string {
        if (type === 'COMPLAINT') {
            return name + ' (Reklamacja)';
        }
        return name;
    }


    private quantityProcessedAtCurrentstage(front: Front): number {
        return front.processings.filter(p => front.stage === p.stage).length;
    }

    updateOrder() {
        const text = "Zapisano zmiany";
        const orderUpdate: UpdateOrder = {
            deadline: this.deadline.valueOf(),
            valuation: this.order.valuation
        };
        this.orderRestService.updateOrder(this.order.id, orderUpdate)
            .subscribe(result => this.displayResponseMsg(result, text))
    }

    displayResponseMsg(result: boolean, text: string): void {
        if (result) {
            this.snackBarService.success(text);
        } else {
            this.snackBarService.failure(`${text} nie powiodła się`);
        }
    }
}

export interface UpdateOrder {
    deadline: number;
    valuation: number;
}