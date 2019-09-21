package com.frontwit.barcodeapp.administration.order.processing.order.infrastructure;

import com.frontwit.barcodeapp.administration.order.processing.order.application.FrontEventsHandler;
import com.frontwit.barcodeapp.administration.order.processing.order.model.OrderRepository;
import com.frontwit.barcodeapp.administration.order.processing.order.model.UpdateStagePolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration("order")
public class BeanConfig {

    @Bean
    OrderQuery orderQuery(MongoTemplate mongoTemplate) {
        return new OrderQuery(mongoTemplate);
    }

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
}
