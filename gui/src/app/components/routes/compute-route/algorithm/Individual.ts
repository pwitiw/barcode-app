import {City} from "./City";

export class Individual {
    dna: City[];

    constructor(dna: City[]) {
        this.dna = dna ? dna.slice() : [];
    }

    getFitness(): number {
        return 1 / this.getDistance();
    }

    getDistance(): number {
        let prev;
        return this.dna.reduce((total, current, index) => {
            if (index === 0) {
                prev = current;
                const last = this.dna[this.dna.length - 1];
                return current.distance(last);
            }
            const result = current.distance(prev);
            prev = current;
            return total + result;
        }, 0);
    }

    mutate(probability: number): Individual {
        const mutatedRoute = this.dna.slice();
        for (let index = 0; index < mutatedRoute.length; index++) {
            if (Math.random() < probability) {

                const randInd = Math.floor(Math.random() * mutatedRoute.length);

                const tempLoc = mutatedRoute[randInd];
                mutatedRoute[randInd] = mutatedRoute[index];
                mutatedRoute[index] = tempLoc;
            }
        }
        return new Individual(mutatedRoute);
    };

    optimize(): Individual {
        let bestDistance = this.getDistance();
        let bestRoute = this.dna;
        for (let i = 0; i < this.dna.length - 1; i++) {
            for (let j = i + 1; j < this.dna.length; j++) {
                const newRoute = this.swap2Opt(this.dna, i, j);
                const newDistance = newRoute.getDistance();
                if (newDistance < bestDistance) {
                    bestDistance = newDistance;
                    bestRoute = newRoute.dna;
                }
            }
        }
        return new Individual(bestRoute);
    }

    swap2Opt(route: City[], i: number, k: number): Individual {
        const reversedPart = route.slice(i, k).reverse();
        const tail = route.slice(k);
        const head = route.slice(0, i);
        const result = head.concat(reversedPart).concat(tail);
        return new Individual(result);
    }

    print(): void {
        const cityNames = this.dna.map((city) => city.name).join(", ");
        const distance = this.getDistance();
        console.log(cityNames + "  ===>  " + distance);
    }

    reorder(city: City): Individual {
        const startingPos = this.dna.indexOf(city);
        const dna = this.dna.slice(startingPos, this.dna.length).concat(this.dna.slice(0, startingPos));
        return new Individual(dna);
    }
}