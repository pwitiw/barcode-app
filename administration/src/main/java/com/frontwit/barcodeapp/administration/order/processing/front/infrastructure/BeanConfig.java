package com.frontwit.barcodeapp.administration.order.processing.front.infrastructure;

import com.frontwit.barcodeapp.administration.order.processing.front.application.ProcessingFront;
import com.frontwit.barcodeapp.administration.order.processing.front.infrastructure.persistence.MongoFrontRepository;
import com.frontwit.barcodeapp.administration.order.processing.front.model.FrontRepository;
import com.frontwit.barcodeapp.administration.order.processing.shared.events.DomainEvents;
import com.frontwit.barcodeapp.administration.order.processing.shared.events.publisher.DomainEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class BeanConfig {

    @Bean
    DomainEvents domainEvents(ApplicationEventPublisher eventPublisher) {
        return new DomainEventPublisher(eventPublisher);
    }

    @Bean
    FrontRepository frontRepository(MongoTemplate mongoTemplate) {
        return new MongoFrontRepository(mongoTemplate);
    }

    @Bean
    ProcessingFront frontProcessing(FrontRepository frontRepository, DomainEvents domainEvents) {
        return new ProcessingFront(frontRepository,domainEvents);
    }
}
