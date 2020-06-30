package com.frontwit.barcodeapp.administration.processing.synchronization.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontwit.barcodeapp.administration.infrastructure.db.CustomerRepository;
import com.frontwit.barcodeapp.administration.processing.front.model.FrontRepository;
import com.frontwit.barcodeapp.administration.processing.order.model.OrderRepository;
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvents;
import com.frontwit.barcodeapp.administration.processing.synchronization.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration("synchronization")
public class BeanConfig {

    @Bean
    JdbcSourceRepository sourceDatabaseOrderRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcSourceRepository(jdbcTemplate);
    }

    @Bean
    OrderMapper orderMapper(ObjectMapper objectMapper) {
        return new OrderMapper(objectMapper);
    }

    @Bean
    OrderSynchronizer synchronizer(SourceRepository sourceRepository,
                                   FrontRepository frontRepository,
                                   OrderRepository orderRepository,
                                   OrderMapper orderMapper,
                                   DomainEvents domainEvents,
                                   SynchronizationRepository synchronizationRepository) {
        return new OrderSynchronizer(
                sourceRepository,
                frontRepository,
                orderRepository,
                orderMapper,
                domainEvents,
                synchronizationRepository
        );
    }

    @Bean
    CustomerSynchronizer customerSynchronizer(CustomerRepository customerRepository, SourceRepository sourceRepository) {
        return new CustomerSynchronizer(customerRepository, sourceRepository);
    }


}
