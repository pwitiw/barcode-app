package com.frontwit.barcodeapp.administration.order.processing.front.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.frontwit.barcodeapp.administration.order.processing.front.application.ProcessingFront;
import com.frontwit.barcodeapp.administration.order.processing.front.infrastructure.messaging.MosquittoCommandHandler;
import com.frontwit.barcodeapp.administration.order.processing.front.infrastructure.persistence.MongoFrontRepository;
import com.frontwit.barcodeapp.administration.order.processing.front.model.FrontProcessingPolicy;
import com.frontwit.barcodeapp.administration.order.processing.front.model.FrontRepository;
import com.frontwit.barcodeapp.administration.order.processing.shared.events.DomainEventPublisher;
import com.frontwit.barcodeapp.administration.order.processing.shared.events.DomainEvents;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration(value = "front")
public class BeanConfig {

    @Bean
    DomainEvents domainEvents(ApplicationEventPublisher eventPublisher) {
        return new DomainEventPublisher(eventPublisher);
    }

    @Bean
    FrontProcessingPolicy frontProcessingPolicy() {
        return FrontProcessingPolicy.allPolicies();
    }

    @Bean
    MongoFrontRepository mongoFrontRepository(MongoTemplate mongoTemplate, FrontProcessingPolicy frontProcessingPolicy) {
        return new MongoFrontRepository(mongoTemplate, frontProcessingPolicy);
    }

    @Bean
    ProcessingFront frontProcessing(FrontRepository frontRepository, DomainEvents domainEvents) {
        return new ProcessingFront(frontRepository, domainEvents);
    }

    @Bean
    ObjectMapper objectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    MosquittoCommandHandler mosquittoCommandHandler(ProcessingFront processingFront, ObjectMapper objectMapper) {
        return new MosquittoCommandHandler(processingFront, objectMapper);
    }
}
