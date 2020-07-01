package com.frontwit.barcodeapp.administration.processing.front.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.frontwit.barcodeapp.administration.processing.front.application.FrontProcessor;
import com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence.MongoFrontRepository;
import com.frontwit.barcodeapp.administration.processing.front.model.FrontProcessingPolicy;
import com.frontwit.barcodeapp.administration.processing.front.model.FrontRepository;
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEventPublisher;
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvents;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration("front")
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
    FrontProcessor frontProcessing(FrontRepository frontRepository, DomainEvents domainEvents) {
        return new FrontProcessor(frontRepository, domainEvents);
    }

    @Bean
    ObjectMapper objectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

}
