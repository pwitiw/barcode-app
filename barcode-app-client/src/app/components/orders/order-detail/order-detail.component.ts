import {Component, Input, OnInit} from '@angular/core';
import {OrderDetails} from 'src/app/components/orders/order-detail/OrderDetails';
import {faBarcode, faArrowsAltV, faArrowsAltH, faIndustry} from '@fortawesome/free-solid-svg-icons';
import {faStackOverflow} from '@fortawesome/free-brands-svg-icons';
import {faComment} from '@fortawesome/free-regular-svg-icons';

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
  iconComment = faComment;

  ngOnInit() {
  }

  getStage(stage: string) {
    switch (stage) {
      case "MILLING": {
        return "Frezowanie"
      }
      case "POLISHING": {
        return "Polerowanie"
      }
      case "BASE": {
        return "Podkładowanie"
      }
      case "GRINDING": {
        return "Szlifiernia"
      }
      case "PAINTING": {
        return "Lakierowanie"
      }
      case "PACKING": {
        return "Spakowane"
      }
      case "SENT": {
        return "Wysłane"
      }
      case "DELIVERD": {
        return "Dostarczone"
      }
      default: {
        return "---"
      }
    }
  }
}
