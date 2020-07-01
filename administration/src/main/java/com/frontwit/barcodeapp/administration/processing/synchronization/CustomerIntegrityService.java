package com.frontwit.barcodeapp.administration.processing.synchronization;

import com.frontwit.barcodeapp.administration.infrastructure.db.CustomerEntity;
import com.frontwit.barcodeapp.administration.processing.front.model.Front;
import com.frontwit.barcodeapp.administration.processing.order.infrastructure.OrderEntity;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
public class CustomerIntegrityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(Front.class);

    private final MongoTemplate mongoTemplate;
    private final CustomerSynchronizer customerSynchronizer;

    @SuppressWarnings("deprecation")
    void assureIntegrity() {
        customerSynchronizer.synchronize();
        var customers = new HashMap<String, CustomerEntity>();
        mongoTemplate.findAll(CustomerEntity.class)
                .forEach(c -> customers.putIfAbsent(c.getName(), c));
        LOGGER.info("Customer amount {}", customers.size());

        List<OrderEntity> orders = mongoTemplate.findAll(OrderEntity.class);
        orders.forEach(o -> {
            var customer = customers.get(o.getCustomer());
            if (customer != null) {
                o.setCustomerId(customer.getId());
                mongoTemplate.save(o);
            } else {
                LOGGER.warn("No customer found for name {}", o.getCustomer());
            }
        });
        LOGGER.info("Modified {} records", orders.size());
    }
}
