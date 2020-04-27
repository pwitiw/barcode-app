package com.frontwit.barcodeapp.administration.catalogue;

import com.frontwit.barcodeapp.administration.catalogue.dto.FrontDto;
import com.frontwit.barcodeapp.administration.catalogue.dto.OrderDetailDto;
import com.frontwit.barcodeapp.administration.catalogue.dto.OrderDto;
import com.frontwit.barcodeapp.administration.catalogue.dto.OrderSearchCriteria;
import com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence.FrontEntity;
import com.frontwit.barcodeapp.administration.processing.order.infrastructure.OrderEntity;
import com.frontwit.barcodeapp.administration.route.planning.dto.DeliveryInfoDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.springframework.data.domain.Sort.Direction.DESC;

@AllArgsConstructor
@Component
public class OrderQuery {

    private MongoTemplate mongoTemplate;
    private CriteriaBuilder criteriaBuilder;

    public OrderDetailDto find(long id) {
        var frontDtos = findFrontsForOrderId(id);
        return Optional.ofNullable(mongoTemplate.findById(id, OrderEntity.class))
                .map(entity -> entity.detailsDto(frontDtos))
                .orElseThrow(() -> new IllegalArgumentException(format("No order for id %s", id)));
    }

    public List<DeliveryInfoDto> findOrdersForRoutes(List<String> routes) {
        var query = new Query(new Criteria("route").in(routes));
        var orderEntities = mongoTemplate.find(query, OrderEntity.class);

        return orderEntities.stream()
                .filter(entity -> !entity.isCompleted() & entity.isPacked())
                .map(OrderEntity::deliveryInfoDto)
                .collect(Collectors.toList());
    }

    private List<FrontDto> findFrontsForOrderId(long orderId) {
        var query = new Query(new Criteria("orderId").is(orderId));
        var frontEntities = mongoTemplate.find(query, FrontEntity.class);
        return frontEntities.stream()
                .map(FrontEntity::dto)
                .collect(Collectors.toList());
    }

    Page<OrderDto> find(Pageable pageable, OrderSearchCriteria searchCriteria) {
        var criteria = criteriaBuilder.build(searchCriteria);
        var query = new Query(criteria).with(pageable).with(Sort.by(DESC, "lastProcessedOn"));
        var orders = mongoTemplate.find(query, OrderEntity.class);
        return PageableExecutionUtils
                .getPage(orders, pageable, () -> mongoTemplate.count(new Query(criteria), OrderEntity.class))
                .map(OrderEntity::dto);
    }
}
