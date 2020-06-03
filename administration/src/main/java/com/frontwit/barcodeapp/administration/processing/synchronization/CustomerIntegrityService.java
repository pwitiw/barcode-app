package com.frontwit.barcodeapp.administration.processing.synchronization;

import com.frontwit.barcodeapp.administration.infrastructure.db.CustomerEntity;
import com.frontwit.barcodeapp.administration.processing.front.model.Front;
import com.frontwit.barcodeapp.administration.processing.order.infrastructure.OrderEntity;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomerIntegrityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(Front.class);

    private final MongoTemplate mongoTemplate;
    private final CustomerSynchronizer customerSynchronizer;

    @PostConstruct
    void assureIntegrity() {
        customerSynchronizer.synchronize();
        var customers = mongoTemplate.findAll(CustomerEntity.class).stream()
                .collect(Collectors.toMap(CustomerEntity::getName, Function.identity()));
        List<OrderEntity> orders = mongoTemplate.find(Query.query(Criteria.where("customerId").is(null)), OrderEntity.class);
        orders.forEach(o -> {
            o.setCustomerId(customers.get(o.getCustomer()).getId());
            mongoTemplate.save(o);
        });
        LOGGER.info("Modified {} records", orders.size());
    }
}
