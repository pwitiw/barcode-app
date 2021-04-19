/// <reference lib="webworker" />
import { GeneticAlgorithm } from "./algorithm/GeneticAlgorithm";
import { City } from "./algorithm/City";

addEventListener('message', ({ data }) => {
    if (!data) return;
    const cities = data.map(c => new City(c.city.name, c.city.lat, c.city.lng));
    try {
        const result = new GeneticAlgorithm(cities).run();
        const cityWithAddress = toCityWithAddressMap(data);
        const res = result.map(city => cityWithAddress.get(city.name));
        postMessage(res);
    } catch (e) {
        console.error(e);
        postMessage(null);
    }
});

function toCityWithAddressMap(addresses) {
    const result = new Map();
    addresses.forEach(address => {
        result.set(address.city.name, address);
    });
    return result;
}