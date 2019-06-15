import {Front} from "./Front";

export interface OrderDetails {
  barcode: string;
  extId: number;
  name: string;
  color: string;
  size: string;
  cutter: string;
  comment: string;
  route: string;
  customer: string;
  stage: string;
  orderedAt: string;
  components: Front[];
}


