package com.frontwit.barcodeapp.dao.repository;

import com.frontwit.barcodeapp.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, Long> {


}
