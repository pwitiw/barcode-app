package com.frontwit.barcodeapp.config;

import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;
import org.bson.BSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDate;

@Configuration
public class MongoConfig {

    private static final String CHANGELOG_PACKAGE = "com.frontwit.barcodeapp";

    @Value("${spring.data.mongodb.database}")
    private String dbName;

    public @Bean
    MongoDbFactory mongoDbFactory() {
        return new SimpleMongoDbFactory(new MongoClient(), dbName);
    }

    public @Bean
    MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) {
        MappingMongoConverter defaultConverter =
                new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory()), new MongoMappingContext());
        defaultConverter.afterPropertiesSet();
        BSON.addDecodingHook(LocalDate.class, arg -> null);
        return new MongoTemplate(mongoDbFactory(), defaultConverter);
    }

    @Bean
    @Autowired
    public Mongobee mongobee(Environment environment) {
        String prefix = "mongodb://";
        String host = environment.getProperty("spring.data.mongodb.host");
        String port = environment.getProperty("spring.data.mongodb.port");
        String uri = prefix + host + ":" + port;
        Mongobee runner = new Mongobee(uri);
        runner.setDbName(dbName);
        runner.setChangeLogsScanPackage(CHANGELOG_PACKAGE);
        return runner;
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