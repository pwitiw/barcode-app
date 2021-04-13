package com.frontwit.barcodeapp.administration.catalogue.routes;

import com.frontwit.barcodeapp.administration.catalogue.orders.dto.CustomerOrdersDto;
import com.frontwit.barcodeapp.administration.catalogue.routes.dto.RouteDetailsDto;
import com.frontwit.barcodeapp.administration.infrastructure.db.CustomerEntity;
import com.frontwit.barcodeapp.administration.processing.order.infrastructure.OrderEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Component
public class RouteQuery {

    private final MongoTemplate mongoTemplate;

    List<CustomerOrdersDto> findCustomersWithOrdersForRoute(String route) {
        List<OrderEntity> orderEntities = getPackedButNotCompletedOrders();
        List<CustomerEntity> customerEntities = getCustomerEntities(orderEntities, route);
        if (customerEntities.isEmpty()) {
            return new ArrayList<>();
        }
        return orderEntities.stream()
                .collect(groupingBy(o -> customerInfoForOrder(customerEntities, o.getCustomerId())))
                .entrySet()
                .stream()
                .filter(e -> e.getKey().isPresent())
                .map(e -> CustomerOrdersDto.of(e.getKey().get(), e.getValue()))
                .collect(toList());
    }

    public List<RouteDetailsDto> getAllRoutes() {
        return mongoTemplate.findAll(RouteEntity.class).stream()
                .map(RouteEntity::dto)
                .collect(toList());
    }

    public List<RouteDetailsDto> getOpenedRoutes() {
        return mongoTemplate.find(notFulfilledRouteOrderByDate(), RouteEntity.class).stream()
                .map(RouteEntity::dto)
                .collect(toList());
    }

    private List<CustomerEntity> getCustomerEntities(List<OrderEntity> orderEntities, String route) {
        List<Long> orderIds = orderEntities.stream().map(OrderEntity::getCustomerId).collect(toList());
        var customerQuery = customerQuery(orderIds, route);
        return mongoTemplate.find(customerQuery, CustomerEntity.class);
    }

    private List<OrderEntity> getPackedButNotCompletedOrders() {
        var query = new Query(new Criteria()
                .and("completed").is(false));
        return mongoTemplate.find(query, OrderEntity.class);
    }

    private Optional<CustomerEntity> customerInfoForOrder(List<CustomerEntity> customers, Long id) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findAny();
    }

    private Query customerQuery(List<Long> ids, String route) {
        return new Query(
                new Criteria("id").in(ids)
                        .and("route").regex(format("^%s", route), "i")
        );
    }

    private Query notFulfilledRouteOrderByDate() {
        return new Query(Criteria.where("fulfilled").is(false))
                .with(Sort.by(Sort.Direction.ASC, "date"));
    }
}
