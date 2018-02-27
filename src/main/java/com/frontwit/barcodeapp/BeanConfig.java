package com.frontwit.barcodeapp;

import com.frontwit.barcodeapp.logic.CounterService;
import com.frontwit.barcodeapp.repository.CounterRepository;
import com.frontwit.barcodeapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CounterService counterService;

    @Bean
    public CounterService counterService(CounterRepository counterRepository) {
        return new CounterService(counterRepository);
    }
}