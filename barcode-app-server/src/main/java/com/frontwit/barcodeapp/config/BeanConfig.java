package com.frontwit.barcodeapp.config;

import com.frontwit.barcodeapp.dao.MongoOrderDao;
import com.frontwit.barcodeapp.domain.order.processing.ports.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;

@Configuration
public class BeanConfig {

//    @Bean
//    public OrderRepository orderDao(OrderRepository orderRepository, MongoOperations mongoOperations) {
//        return new MongoOrderDao(orderRepository, mongoOperations);
//    }

//    @Bean
//    public OrderService orderService(OrderRepository orderRepository, RouteDao routeDao, BarcodeGeneratorService barcodeGeneratorService) {
//        return new OrderService(orderRepository, routeDao, barcodeGeneratorService);
//    }ó
}