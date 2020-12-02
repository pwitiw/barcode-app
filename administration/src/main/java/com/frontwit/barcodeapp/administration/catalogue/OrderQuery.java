package com.frontwit.barcodeapp.administration.catalogue;

import com.frontwit.barcodeapp.administration.catalogue.dto.*;
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

import java.util.List;
import java.util.Optional;

import static com.frontwit.barcodeapp.administration.catalogue.CriteriaBuilder.COMPLETED_FIELD;
import static com.frontwit.barcodeapp.administration.catalogue.CriteriaBuilder.DEADLINE_FIELD;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@AllArgsConstructor
@Component
public class OrderQuery {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderQuery.class);

    private final MongoTemplate mongoTemplate;
    private final CriteriaBuilder criteriaBuilder;
    private final CustomerRepository customerRepository;

    public OrderDetailDto getOrderDetails(long id) {
        var frontDtos = findFrontsForOrderId(id);
        return Optional.ofNullable(mongoTemplate.findById(id, OrderEntity.class))
                .map(entity -> entity.detailsDto(frontDtos, findCustomerBy(entity.getCustomerId())))
                .orElseThrow(() -> new IllegalArgumentException(format("No order for id %s", id)));
    }

    Page<OrderDto> getOrders(Pageable pageable, OrderSearchCriteria searchCriteria) {
        var criteria = criteriaBuilder.build(searchCriteria);
        var query = new Query(criteria).with(pageable).with(Sort.by(DESC, "lastProcessedOn"));
        var orders = mongoTemplate.find(query, OrderEntity.class);
        return PageableExecutionUtils
                .getPage(orders, pageable, () -> mongoTemplate.count(new Query(criteria), OrderEntity.class))
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


    private List<FrontDto> findFrontsForOrderId(long orderId) {
        var query = new Query(new Criteria("orderId").is(orderId));
        var frontEntities = mongoTemplate.find(query, FrontEntity.class);
        return frontEntities.stream()
                .map(FrontEntity::dto)
                .collect(toList());
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
