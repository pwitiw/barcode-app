package com.frontwit.barcodeapp.administration.catalogue.orders;

import com.frontwit.barcodeapp.administration.catalogue.orders.dto.*;
import com.frontwit.barcodeapp.administration.infrastructure.db.CustomerEntity;
import com.frontwit.barcodeapp.administration.infrastructure.db.CustomerRepository;
import com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence.FrontEntity;
import com.frontwit.barcodeapp.administration.processing.order.infrastructure.OrderEntity;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.frontwit.barcodeapp.administration.catalogue.orders.OrderCriteriaBuilder.COMPLETED_FIELD;
import static com.frontwit.barcodeapp.administration.catalogue.orders.OrderCriteriaBuilder.DEADLINE_FIELD;
import static java.lang.String.format;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@AllArgsConstructor
@Component
public class OrderQuery {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderQuery.class);

    private final MongoTemplate mongoTemplate;
    private final OrderCriteriaBuilder orderCriteriaBuilder;
    private final CustomerCriteriaBuilder customerCriteriaBuilder;
    private final CustomerRepository customerRepository;

    public OrderDetailDto getOrderDetails(long id) {
        var frontDtos = findFrontsForOrderIds(Collections.singletonList(id));
        return Optional.ofNullable(mongoTemplate.findById(id, OrderEntity.class))
                .map(entity -> entity.detailsDto(frontDtos, findCustomerBy(entity.getCustomerId())))
                .orElseThrow(() -> new IllegalArgumentException(format("No order for id %s", id)));
    }

    public List<OrderDetailDto> getOrderDetails(Collection<Long> orderIds) {
        var fronts = findFrontsForOrderIds(orderIds).stream()
                .collect(Collectors.groupingBy(FrontEntity::getOrderId));
        return mongoTemplate.find(new Query(Criteria.where("id").in(orderIds)), OrderEntity.class).stream()
                .map(entity -> entity.detailsDto(fronts.get(entity.getId()), findCustomerBy(entity.getCustomerId())))
                .collect(Collectors.toList());
    }

    Page<OrderDto> getOrders(Pageable pageable, OrderSearchCriteria searchCriteria) {
        var orderCriteria = orderCriteriaBuilder.build(searchCriteria);
        var customerCriteria = customerCriteriaBuilder.build(searchCriteria);
        var criteria = orderCriteria.andOperator(customerCriteria);
        var query = new Query(criteria).with(pageable).with(Sort.by(DESC, "lastProcessedOn"));
        var orders = mongoTemplate.find(query, OrderEntity.class);
        return PageableExecutionUtils
                .getPage(orders, pageable, () -> mongoTemplate.count(new Query(orderCriteria), OrderEntity.class))
                .map(o -> o.dto(findCustomerBy(o.getCustomerId())));
    }

    private CustomerEntity findCustomerBy(Long id) {
        if (id == null) {
            return new CustomerEntity();
        }
        return customerRepository.findBy(id).orElseGet(() -> {
            LOGGER.warn("No customer for id {}", id);
            return new CustomerEntity();
        });
    }

    private List<FrontEntity> findFrontsForOrderIds(Collection<Long> orderIds) {
        var query = new Query(Criteria.where("orderId").in(orderIds));
        return mongoTemplate.find(query, FrontEntity.class);
    }

    Page<ReminderDto> findDeadlines(Pageable pageable) {
        var criteria = Criteria.where(DEADLINE_FIELD).ne(null).and(COMPLETED_FIELD).is(false);
        var query = new Query(criteria).with(pageable).with(Sort.by(ASC, DEADLINE_FIELD));
        var orders = mongoTemplate.find(query, OrderEntity.class);

        return PageableExecutionUtils
                .getPage(orders, pageable, () -> mongoTemplate.count(new Query(criteria), OrderEntity.class))
                .map(o -> o.reminderDto(findCustomerBy(o.getCustomerId())));
    }
}
