package com.frontwit.barcodeapp.administration.processing.order.infrastructure;

import com.frontwit.barcodeapp.administration.infrastructure.db.mongo.MongoCustomerRepository;
import com.frontwit.barcodeapp.administration.processing.order.application.FrontEventsHandler;
import com.frontwit.barcodeapp.administration.processing.order.model.OrderRepository;
import com.frontwit.barcodeapp.administration.processing.order.model.UpdateStagePolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration("order")
public class BeanConfig {
    @Bean
    UpdateStagePolicy updateStagePolicy() {
        return UpdateStagePolicy.allPolicies();
    }

    @Bean
    MongoOrderRepository mongoOrderRepository(MongoTemplate mongoTemplate, UpdateStagePolicy policy) {
        return new MongoOrderRepository(mongoTemplate, policy);
    }

    @Bean
    FrontEventsHandler frontEventsHandler(OrderRepository orderRepository) {
        return new FrontEventsHandler(orderRepository);
    }

    @Bean
    MongoCustomerRepository mongoCustomerRepository(MongoTemplate mongoTemplate) {
        return new MongoCustomerRepository(mongoTemplate);
    }
}
