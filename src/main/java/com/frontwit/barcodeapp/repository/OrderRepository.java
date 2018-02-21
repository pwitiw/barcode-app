package com.frontwit.barcodeapp.repository;

import com.frontwit.barcodeapp.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order,Long>{

}
