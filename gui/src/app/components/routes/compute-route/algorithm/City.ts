export class City {
    name: string;
    x: number;
    y: number;

    constructor(name, x, y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    distance(city): number {
        return Math.hypot(this.x - city.x, this.y - city.y);
    }
}