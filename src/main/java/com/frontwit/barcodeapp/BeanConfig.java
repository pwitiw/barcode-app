package com.frontwit.barcodeapp;

import com.frontwit.barcodeapp.logic.CounterService;
import com.frontwit.barcodeapp.logic.OrderService;
import com.frontwit.barcodeapp.logic.OrderServiceImpl;
import com.frontwit.barcodeapp.mapper.OrderMapper;
import com.frontwit.barcodeapp.repository.CounterRepository;
import com.frontwit.barcodeapp.repository.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@RestController
public class BeanConfig {

    @Bean
    public CounterService counterService(CounterRepository counterRepository) {
        return new CounterService(counterRepository);
    }

    @Bean
    public OrderService orderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        return new OrderServiceImpl(orderRepository, orderMapper);
    }
}