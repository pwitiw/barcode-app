export class DeliveryInformation {
    customer: string;
    orders: Order[];
    paymentType: string;

    static of(customer: string, orders: Order[], paymentType: string): DeliveryInformation {
        const deliveryInformation = new DeliveryInformation();
        deliveryInformation.customer = customer;
        deliveryInformation.orders = orders;
        deliveryInformation.paymentType = paymentType;
        return deliveryInformation;
    }

    isIncludedInPlanning(): boolean {
        return this.orders.filter(order => order.isSelected).length > 0;
    }

    displayAggregateOrders(): string {
        const displayOrder = (o: Order) => `${o.name} - ${o.quantity} szt.`;
        return this.orders.filter(o => o.isSelected).map(displayOrder).reduce((o1, o2) => o1 + "<br>" + o2);
    }

    calculatePrice(): number {
        return this.orders.filter(o => o.isSelected).map(o => o.price).reduce((o1, o2) => o1 + o2);
    }
}

export interface Order {
    name: string;
    quantity: number;
    price: number;
    isSelected?: boolean;
}


