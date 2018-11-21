package com.frontwit.barcodeapp.dao;

import com.frontwit.barcodeapp.dao.repository.RouteRepository;
import com.frontwit.barcodeapp.model.Route;

public class RouteDao {

    RouteRepository repository;

    public RouteDao(RouteRepository routeRepository) {
        this.repository = routeRepository;
    }

    public Route findByName(String name) {
        return repository.findByName(name);
    }
}
