package com.frontwit.barcodeapp;

import com.frontwit.barcodeapp.dao.OrderDao;
import com.frontwit.barcodeapp.dao.repository.CounterRepository;
import com.frontwit.barcodeapp.dao.repository.OrderRepository;
import com.frontwit.barcodeapp.logic.CounterService;
import com.frontwit.barcodeapp.logic.OrderService;
import com.frontwit.barcodeapp.logic.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@RestController
public class BeanConfig {

    @Bean
    public CounterService counterService(CounterRepository counterRepository) {
        return new CounterService(counterRepository);
    }

    @Bean
    public OrderDao orderDao(OrderRepository orderRepository, MongoOperations mongoOperations) {
        return new OrderDao(orderRepository, mongoOperations);
    }

    @Bean
    public OrderService orderService(OrderDao orderDao) {
        return new OrderServiceImpl(orderDao);
    }
}