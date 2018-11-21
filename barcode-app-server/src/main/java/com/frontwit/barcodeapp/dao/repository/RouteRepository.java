package com.frontwit.barcodeapp.dao.repository;

import com.frontwit.barcodeapp.model.Route;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RouteRepository extends MongoRepository<Route, ObjectId> {

    Route findByName(String name);

}
