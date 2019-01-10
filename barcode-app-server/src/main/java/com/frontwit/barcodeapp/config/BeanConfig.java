package com.frontwit.barcodeapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontwit.barcodeapp.dao.MongoOrderDao;
import com.frontwit.barcodeapp.dao.OrderDao;
import com.frontwit.barcodeapp.dao.RouteDao;
import com.frontwit.barcodeapp.dao.repository.CounterRepository;
import com.frontwit.barcodeapp.dao.repository.OrderRepository;
import com.frontwit.barcodeapp.logic.*;
import com.frontwit.barcodeapp.synchronization.SynchronizationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityManager;

@Configuration
public class BeanConfig {

    @Bean
    public BarcodeGeneratorService counterService(CounterRepository counterRepository) {
        return new BarcodeGeneratorService(counterRepository);
    }

    @Bean
    public OrderDao orderDao(OrderRepository orderRepository, MongoOperations mongoOperations) {
        return new MongoOrderDao(orderRepository, mongoOperations);
    }

    @Bean
    public OrderService orderService(OrderDao orderDao, RouteDao routeDao, BarcodeGeneratorService barcodeGeneratorService) {
        return new OrderService(orderDao, routeDao, barcodeGeneratorService);
    }

    @Bean
    public SynchronizationService synchronizationService(EntityManager entityManager) {
        return new SynchronizationService(entityManager, new ObjectMapper());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}