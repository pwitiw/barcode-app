import { Order } from './Order';

export const ORDERS: Order[] = [
  {
    id: 1, name: "zamownienie 1", orderDate: "01/02/2020", isComplete:false, components:[
      {id: 1, name:"cyka blat", width:20, height:40, barCode:"barcode1"},
      {id: 2, name:"front szuflady", width:120, height:20, barCode:"barcode2"}]
  },
  {
    id: 2, name: "zamownienie 2", orderDate: "05/03/2020", isComplete:false, components:[
      {id: 3, name:"drzwi", width:100, height:240, barCode:"barcode3"},
      {id: 4, name:"front zmywary", width:120, height:120, barCode:"barcode4"}]
  },
  {
    id: 3, name: "zamownienie 3", orderDate: "11/04/2020", isComplete:false, components:[
      {id: 5, name:"cyka blyat", width:320, height:340, barCode:"barcode5"},
      {id: 6, name:"niewiadomo", width:20, height:20, barCode:"barcode6"}]
  }
]
