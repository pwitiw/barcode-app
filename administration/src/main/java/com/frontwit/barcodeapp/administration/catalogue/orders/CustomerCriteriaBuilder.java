package com.frontwit.barcodeapp.administration.catalogue.orders;

import com.frontwit.barcodeapp.administration.catalogue.orders.dto.OrderSearchCriteria;
import com.frontwit.barcodeapp.administration.infrastructure.db.CustomerEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.springframework.util.StringUtils.isEmpty;

@Service
@AllArgsConstructor
public class CustomerCriteriaBuilder {
    private final MongoTemplate mongoTemplate;

    Criteria build(OrderSearchCriteria searchCriteria) {
        final var result = new Criteria();
        name(searchCriteria, result);
        route(searchCriteria, result);
        Set<Long> ids = mongoTemplate.find(new Query(result), CustomerEntity.class).stream()
                .map(CustomerEntity::getId)
                .collect(Collectors.toSet());
        return new Criteria().and("customerId").in(ids);
    }

    private void name(OrderSearchCriteria searchCriteria, Criteria result) {
        if (isNotEmpty(searchCriteria.getCustomer())) {
            addRegex("name", searchCriteria.getCustomer(), result);
        }
    }

    private void route(OrderSearchCriteria searchCriteria, Criteria result) {
        if (isNotEmpty(searchCriteria.getRoute())) {
            addRegex("route", searchCriteria.getRoute(), result);
        }
    }

    private static void addRegex(String field, String value, Criteria result) {
        result.and(field).regex(format("%s", value), "i");
    }

    private static boolean isNotEmpty(String arg) {
        return !isEmpty(arg);
    }
}
