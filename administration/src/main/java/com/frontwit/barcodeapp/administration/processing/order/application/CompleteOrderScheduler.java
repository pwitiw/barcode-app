package com.frontwit.barcodeapp.administration.processing.order.application;

import com.frontwit.barcodeapp.administration.processing.order.infrastructure.OrderEntity;
import com.mongodb.client.result.UpdateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class CompleteOrderScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompleteOrderScheduler.class);
    private static final String COMPLETED_FIELD = "completed";

    private final Integer maxOrderAge;
    private final MongoTemplate mongoTemplate;

    public CompleteOrderScheduler(@Value("${max.order.age.days}") Integer maxOrderAge,
                                  MongoTemplate mongoTemplate) {
        this.maxOrderAge = maxOrderAge;
        this.mongoTemplate = mongoTemplate;
    }

    @Scheduled(cron = "0 0 * * * *")
    void markOrdersAsCompleted() {
        Instant threshold = Instant.now().minus(maxOrderAge, ChronoUnit.DAYS);
        Update markAsCompletedStatement = new Update().set(COMPLETED_FIELD, true);
        UpdateResult result = mongoTemplate.updateMulti(
                ordersNotCompleteAndOrderedAfter(threshold),
                markAsCompletedStatement,
                OrderEntity.class
        );
        LOGGER.info("Automatically completed {} orders because they were older than {} days", result.getModifiedCount(), maxOrderAge);
    }

    private static Query ordersNotCompleteAndOrderedAfter(Instant instant) {
        Criteria criteria = new Criteria()
                .and(COMPLETED_FIELD).is(false)
                .and("orderedAt").lte(instant);

        return new Query(criteria);
    }
}
