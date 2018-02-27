package com.frontwit.barcodeapp;

import com.frontwit.barcodeapp.entity.Order;
import com.frontwit.barcodeapp.repository.OrderRepository;
import com.github.mongobee.Mongobee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Configuration
public class BeanConfig {


    @Autowired
    OrderRepository orderRepository;

    @PostConstruct
    public void test() {
        Order order = Order.builder().id(1L).name("TestName").build();
        Order order2 = Order.builder().id(2L).name("TestName2").build();
        orderRepository.save(order);
        orderRepository.save(order2);
    }
}