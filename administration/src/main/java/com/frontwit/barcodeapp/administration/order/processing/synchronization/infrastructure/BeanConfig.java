package com.frontwit.barcodeapp.administration.order.processing.synchronization.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontwit.barcodeapp.administration.order.processing.shared.events.DomainEvents;
import com.frontwit.barcodeapp.administration.order.processing.synchronization.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration(value = "synchronization")
public class BeanConfig {

    @Bean
    SourceDatabaseOrderRepository sourceDatabaseOrderRepository(JdbcTemplate jdbcTemplate) {
        return new SourceDatabaseOrderRepository(jdbcTemplate);
    }

    @Bean
    OrderMapper orderMapper(ObjectMapper objectMapper) {
        return new OrderMapper(objectMapper);
    }

    @Bean
    OrderSynchronizer synchronizer(SourceOrderRepository sourceOrderRepository,
                                   SaveSynchronizedFronts saveSynchronizedFronts,
                                   SaveSynchronizedOrder saveSynchronizedOrder,
                                   OrderMapper orderMapper,
                                   DomainEvents domainEvents) {
        return new OrderSynchronizer(sourceOrderRepository,
                saveSynchronizedFronts,
                saveSynchronizedOrder,
                orderMapper,
                domainEvents);
    }

}
