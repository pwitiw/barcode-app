import {City} from "./City";

export class CustomerAddress {
    customer: string;
    address: string;
    city: City;

    constructor(customer: string, address: string) {
        this.customer = customer;
        this.address = address;
    }

    normalizedAddress(): string {
        return !this.address
            ?
            "" :
            this.address
                .toLowerCase()
                .replace("ł", "l")
                .replace("ż", "z")
                .replace("ź", "z")
                .replace("ą", "a")
                .replace("ę", "e")
                .replace("ć", "c")
                .replace("ó", "o")
                .replace("ś", "s")
                .replace("ń", "n");
    }
}
//