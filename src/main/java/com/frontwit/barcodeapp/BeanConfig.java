package com.frontwit.barcodeapp;

import com.frontwit.barcodeapp.logic.CounterService;
import com.frontwit.barcodeapp.repository.CounterRepository;
import com.frontwit.barcodeapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@RestController
public class BeanConfig {

    @Autowired
    OrderRepository orderRepository;

    @Bean
    public CounterService counterService(CounterRepository counterRepository) {
        return new CounterService(counterRepository);
    }
}