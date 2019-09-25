package com.frontwit.barcodeapp.administration.order.processing.order.infrastructure;

import com.frontwit.barcodeapp.administration.order.processing.front.infrastructure.persistence.FrontEntity;
import com.frontwit.barcodeapp.administration.order.processing.order.application.dto.FrontDto;
import com.frontwit.barcodeapp.administration.order.processing.order.application.dto.OrderDetailDto;
import com.frontwit.barcodeapp.administration.order.processing.order.application.dto.OrderDto;
import com.frontwit.barcodeapp.administration.order.processing.order.application.dto.OrderSearchCriteria;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

@AllArgsConstructor
public class OrderQuery {

    private MongoTemplate mongoTemplate;

    public OrderDetailDto find(long id) {
        var frontDtos = findFrontsForOrderId(id);
        return Optional.ofNullable(mongoTemplate.findById(id, OrderEntity.class))
                .map(entity -> entity.detailsDto(frontDtos))
                .orElseThrow(() -> new IllegalArgumentException(format("No order for id %s", id)));
    }

    private List<FrontDto> findFrontsForOrderId(long orderId) {
        var query = new Query(new Criteria("orderId").is(orderId));
        var frontEntities = Optional.ofNullable(mongoTemplate.find(query, FrontEntity.class)).orElse(new ArrayList<>());
        return frontEntities.stream()
                .map(FrontEntity::dto)
                .collect(Collectors.toList());
    }

    public Page<OrderDto> find(Pageable pageable, OrderSearchCriteria searchCriteria) {
        var criteria = createCriteria(searchCriteria);
        var query = new Query(criteria).with(pageable);
        var orders = mongoTemplate.find(query, OrderEntity.class);
        return PageableExecutionUtils
                .getPage(orders, pageable, () -> mongoTemplate.count(query, OrderEntity.class))
                .map(OrderEntity::dto);
    }

    private Criteria createCriteria(OrderSearchCriteria searchCriteria) {
        var criteria = new Criteria();
        if (isNotEmpty(searchCriteria.getName())) {
            criteria.and("name").regex(format("^%s", searchCriteria.getName()), "i");
        }
        if (searchCriteria.getCompleted() != null && searchCriteria.getCompleted()) {
            criteria.and("isCompleted").is(true);
        } else {
            criteria.and("isCompleted").is(false);
        }
        return criteria;
    }

    private boolean isNotEmpty(String arg) {
        return arg != null && !"".equals(arg);
    }
}
