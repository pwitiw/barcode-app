package com.frontwit.barcodeapp.administration.processing.synchronization;

import com.frontwit.barcodeapp.administration.processing.order.infrastructure.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static com.frontwit.barcodeapp.administration.processing.order.model.OrderType.COMPLAINT;

@Service
@RequiredArgsConstructor
public class OrderComplaintsNameUpdater {

    private static final String COMPLAINT_SUFFIX = " (R)";
    private final MongoTemplate mongoTemplate;

    @Scheduled(initialDelay = 1_000, fixedDelay = Long.MAX_VALUE)
    void update() {
        mongoTemplate.find(new Query(Criteria.where("completed").is(false)), OrderEntity.class)
                .forEach(order -> {
                    if (order.getType() == COMPLAINT && !order.getName().contains(COMPLAINT_SUFFIX)) {
                        order.setName(order.getName() + COMPLAINT_SUFFIX);
                        mongoTemplate.save(order);
                    }
                });
    }
}
