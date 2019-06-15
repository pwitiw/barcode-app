package com.frontwit.barcodeapp.infrastructure.persistence;

import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConfig {

    @Value("${spring.data.mongodb.uri}")
    private String uri;

    @Bean
    public MongoDbFactory mongoDbFactory() {
        MongoClientURI mongoClientURI = new MongoClientURI(uri);
        return new SimpleMongoDbFactory(mongoClientURI);
    }

    @Bean
    @Autowired
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory, getDefaultMongoConverter(mongoDbFactory));
    }

    @Bean
    public MappingMongoConverter getDefaultMongoConverter(MongoDbFactory mongoDbFactory) {
        List<Converter> converters = new ArrayList<>();
        converters.add(new StringToLocalDateTimeConverter());
        converters.add(new StringToLocalDateConverter());
        CustomConversions customConversions = new CustomConversions(CustomConversions.StoreConversions.NONE, converters);
        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), new MongoMappingContext());
        converter.afterPropertiesSet();
        converter.setCustomConversions(customConversions);
        return converter;
    }

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener() {
        return new ValidatingMongoEventListener(validator());
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public LoggingEventListener mappingEventsListener() {
        return new LoggingEventListener();
    }

}