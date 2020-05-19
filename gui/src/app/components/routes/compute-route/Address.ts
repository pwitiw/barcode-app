export class Address {
    name: string;

    constructor(name: string) {
        this.name = name;
    }

    normalized(): string {
        return !this.name
            ?
            "" :
            this.name
                .toLowerCase()
                .replace("ł", "l")
                .replace("ż", "z")
                .replace("ź", "z")
                .replace("ą", "a")
                .replace("ę", "e")
                .replace("ć", "c")
                .replace("ó", "o")
                .replace("ś", "s")
                .replace("ń", "n")
    }
}