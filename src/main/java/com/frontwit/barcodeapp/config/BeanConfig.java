package com.frontwit.barcodeapp.config;

import com.frontwit.barcodeapp.dao.OrderDao;
import com.frontwit.barcodeapp.dao.repository.CounterRepository;
import com.frontwit.barcodeapp.dao.repository.OrderRepository;
import com.frontwit.barcodeapp.logic.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BeanConfig {

    @Bean
    public BarcodeService counterService(CounterRepository counterRepository) {
        return new BarcodeService(counterRepository);
    }

    @Bean
    public OrderDao orderDao(OrderRepository orderRepository, MongoOperations mongoOperations) {
        return new OrderDao(orderRepository, mongoOperations);
    }

    @Bean
    public OrderService orderService(OrderDao orderDao, BarcodeService barcodeService) {
        return new OrderServiceImpl(orderDao, barcodeService);
    }

    @Bean
    public BarcodeService barcodeService(CounterRepository counterRepository) {
        return new BarcodeService(counterRepository);
    }

    @Bean
    public PdfService pdfService() {
        return new PdfService();
    }


    @Bean
    public CsvService csvService() {
        return new CsvService();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}