/// <reference lib="webworker" />
import {GeneticAlgorithm} from "./algorithm/GeneticAlgorithm";
import {City} from "./algorithm/City";

addEventListener('message', ({data}) => {
    if (!data) return;
    const cities = data.map(city => new City(city.name, city.lat, city.lng));
    const start = performance.now();
    const result = new GeneticAlgorithm(cities).run();
    const time = Math.round(performance.now() - start) / 1000;
    console.log("Czas: " + time + " s");
    postMessage(result.map(c => {
            return {name: c.name, lat: c.x, lng: c.y}
        }
    ));
});
