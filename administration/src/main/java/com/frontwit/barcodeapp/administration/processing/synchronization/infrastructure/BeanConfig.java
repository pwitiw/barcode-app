package com.frontwit.barcodeapp.administration.processing.synchronization.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvents;
import com.frontwit.barcodeapp.administration.processing.synchronization.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration(value = "synchronization")
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
                                   SaveSynchronizedFronts saveSynchronizedFronts,
                                   SaveSynchronizedOrder saveSynchronizedOrder,
                                   SaveSynchronizedCustomer saveSynchronizedCustomer,
                                   CheckSynchronizedOrder checkSynchronizedOrder,
                                   OrderMapper orderMapper,
                                   DomainEvents domainEvents,
                                   SynchronizationRepository synchronizationRepository) {
        return new OrderSynchronizer(sourceRepository,
                saveSynchronizedFronts,
                saveSynchronizedOrder,
                saveSynchronizedCustomer,
                checkSynchronizedOrder,
                orderMapper,
                domainEvents,
                synchronizationRepository);
    }

    @Bean
    CustomerSynchronizer customerSynchronizer(SaveSynchronizedCustomer saveSynchronizedCustomer, SourceRepository sourceRepository) {
        return new CustomerSynchronizer(saveSynchronizedCustomer, sourceRepository);
    }


}
