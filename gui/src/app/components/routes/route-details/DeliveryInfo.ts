export class DeliveryInfo {
    customer: string;
    orders: Order[];
    paymentType: string;
    address: string;
    phoneNumber: string;

    static of(customer: string, orders: Order[], paymentType: string, address: string, phoneNumber: string): DeliveryInfo {
        const deliveryInformation = new DeliveryInfo();
        deliveryInformation.customer = customer;
        deliveryInformation.orders = orders;
        deliveryInformation.paymentType = paymentType;
        deliveryInformation.address = address;
        deliveryInformation.phoneNumber = phoneNumber;
        return deliveryInformation;
    }

    containsOrder(orderId: number) {
        return this.orders.filter(o => o.id === orderId).length > 0;
    }

    displayAggregateOrders(): string {
        const displayOrder = (o: Order) => `${o.name} - ${o.quantity} szt.`;
        return this.orders.map(displayOrder).reduce((o1, o2) => o1 + "<br>" + o2);
    }

    calculatePrice(): number {
        return this.orders.map(o => o.valuation).reduce((o1, o2) => o1 + o2);
    }
}

export interface Order {
    id: number;
    name: string;
    quantity: number;
    valuation: number;
    orderedAt: string;
    cutter: string;
    color: string;
}