package com.frontwit.barcodeapp.config;

import com.frontwit.barcodeapp.dao.OrderDao;
import com.frontwit.barcodeapp.dao.repository.CounterRepository;
import com.frontwit.barcodeapp.dao.repository.OrderRepository;
import com.frontwit.barcodeapp.logic.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@RestController
public class BeanConfig {

    @Bean
    public BarcodeGenerator counterService(CounterRepository counterRepository) {
        return new BarcodeGenerator(counterRepository);
    }

    @Bean
    public OrderDao orderDao(OrderRepository orderRepository, MongoOperations mongoOperations) {
        return new OrderDao(orderRepository, mongoOperations);
    }

    @Bean
    public OrderService orderService(OrderDao orderDao) {
        return new OrderServiceImpl(orderDao);
    }

    @Bean
    public BarcodeService barcodeService(PdfGenerator pdfGenerator) {
        return new BarcodeService(pdfGenerator);
    }

    @Bean
    public PdfGenerator pdfGenerator() {
        return new PdfGenerator();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}