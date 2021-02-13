package com.frontwit.barcodeapp.administration.catalogue.routes;

import com.frontwit.barcodeapp.administration.catalogue.routes.dto.RouteDetailsDto;
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvents;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static java.util.stream.Collectors.toSet;


@AllArgsConstructor
@Service
public class RouteCommand {

    private final MongoTemplate mongoTemplate;
    private final DomainEvents domainEvents;

    public String save(RouteDetailsDto dto) {
        var entity = RouteEntity.of(dto);
        mongoTemplate.save(entity);
        return entity.getId();
    }

    public void delete(String id) {
        mongoTemplate.remove(new Query(Criteria.where("id").is(id)), RouteEntity.class);
    }

    public void fulfill(String id) {
        RouteEntity entity = mongoTemplate.findById(id, RouteEntity.class);
        if (entity != null) {
            entity.setFulfilled(true);
            mongoTemplate.save(entity);
            var orderIds = entity.getDeliveryInfos().stream()
                    .map(DeliveryInfoEntity::getOrders)
                    .flatMap(Collection::stream)
                    .map(DeliveryOrderEntity::getId)
                    .collect(toSet());

            domainEvents.publish(new RouteCompleted(entity.getId(), entity.getName(), orderIds));
        }
    }
}
