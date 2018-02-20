package com.frontwit.barcodeapp.repository;

import com.frontwit.barcodeapp.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order,Long>{

    List<Order> findById(Long id);

}
