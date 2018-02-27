package com.frontwit.barcodeapp.repository;

import com.frontwit.barcodeapp.entity.Counter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CounterRepository extends MongoRepository<Counter, String> {

}
