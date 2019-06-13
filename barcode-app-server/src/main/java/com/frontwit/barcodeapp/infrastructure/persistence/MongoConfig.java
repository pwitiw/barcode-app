//package com.frontwit.barcodeapp.infrastructure.persistence;
//
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientOptions;
//import com.mongodb.MongoClientURI;
//import org.bson.BSON;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.MongoDbFactory;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
//import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
//import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
//import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
//import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;
//import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
//import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
//
//import java.time.LocalDate;
//
//@Configuration
//public class MongoConfig {
//
//    private static final String CHANGELOG_PACKAGE = "com.frontwit.barcodeapp";
//
//    @Value("${spring.data.mongodb.uri}")
//    private String uri;
//    @Value("${spring.data.mongodb.host}")
//    private String host;
//    @Value("${spring.data.mongodb.port}")
//    private String port;
//    @Value("${spring.data.mongodb.database}")
//    private String database;
//    @Value("${spring.data.mongodb.username}")
//    private String username;
//    @Value("${spring.data.mongodb.password}")
//    private String password;
////
////    @Bean
////    public MongoDbFactory mongoDbFactory() {
////        MongoClientURI mongoClientURI = new MongoClientURI(uri);;
////        return new SimpleMongoDbFactory();
////    }
//
//    @Bean
//    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) {
//        MappingMongoConverter defaultConverter =
//                new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), new MongoMappingContext());
//        defaultConverter.afterPropertiesSet();
//        BSON.addDecodingHook(LocalDate.class, arg -> null);
//        return new MongoTemplate(mongoDbFactory, defaultConverter);
//    }
//
//    @Bean
//    public ValidatingMongoEventListener validatingMongoEventListener() {
//        return new ValidatingMongoEventListener(validator());
//    }
//
//    @Bean
//    public LocalValidatorFactoryBean validator() {
//        return new LocalValidatorFactoryBean();
//    }
//
//    @Bean
//    public LoggingEventListener mappingEventsListener() {
//        return new LoggingEventListener();
//    }
//
//}