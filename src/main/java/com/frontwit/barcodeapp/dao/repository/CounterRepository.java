package com.frontwit.barcodeapp.dao.repository;

import com.frontwit.barcodeapp.model.Counter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CounterRepository extends MongoRepository<Counter, String> {

}
