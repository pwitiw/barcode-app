import {Population} from "./Population";
import {City} from "./City";
import {Individual} from "./Individual";

const MAX_GENERATIONS = 1000;
const POPULATION = 50;
const CROSSOVER_PROBABILITY = 0.7;
const MUTATION_PROBABILITY = 0.1;

export class GeneticAlgorithm {
    cities: City[];

    constructor(cities: City[]) {
        this.cities = cities ? cities : [];
    }

    run(): City[] {
        if (this.cities.length < 4) {
            return this.cities;
        }
        let population = new Population(POPULATION, this.cities, CROSSOVER_PROBABILITY, MUTATION_PROBABILITY);
        let bestScore = population.getFittest();
        for (let generation = 0; generation < MAX_GENERATIONS; generation++) {
            population.nextGeneration();
            bestScore = this.getBestScore(bestScore, population.getFittest());
        }
        bestScore = bestScore.reorder(this.cities[0]);
        bestScore = bestScore.optimize();
        return bestScore.dna;
    }

    getBestScore(bestScore: Individual, fittest: Individual): Individual {
        return bestScore.getFitness() < fittest.getFitness() ? fittest : bestScore;
    }
}