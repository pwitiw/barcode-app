package com.frontwit.barcodeapp.dao;

import com.frontwit.barcodeapp.dao.repository.OrderRepository;
import com.frontwit.barcodeapp.dto.OrderSearchCriteria;
import com.frontwit.barcodeapp.model.Order;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class OrderDao {

    private OrderRepository repository;
    private MongoOperations mongoOps;

    public OrderDao(OrderRepository repository, MongoOperations mongoOps) {
        this.repository = repository;
        this.mongoOps = mongoOps;
    }

    public Order findOne(String id) {
        Order order= repository.findOne(new ObjectId(id));
        if(order ==null){
            throw new IllegalArgumentException();
        }
    return order;
    }

    public Order save(Order order) {
        return repository.save(order);
    }

    public Page<Order> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Order> findForCriteria(Pageable pageable, OrderSearchCriteria searchCriteria) {
        if (OrderSearchCriteria.isEmpty(searchCriteria)) {
            return findAll(pageable);
        }
        return findOrdersForCriteria(pageable, searchCriteria);
    }

    private Page<Order> findOrdersForCriteria(Pageable pageable, OrderSearchCriteria searchCriteria) {

        Criteria appliedCriterias = getAppliedCriteria(searchCriteria);
        Query query = Query.query(appliedCriterias).with(pageable);
        List<Order> orders = mongoOps.find(query, Order.class);

        return PageableExecutionUtils.getPage(
                orders,
                pageable,
                () -> repository.count());
    }

    private Criteria getAppliedCriteria(OrderSearchCriteria searchCriteria) {
        List<Criteria> criterias = new ArrayList<>();

        if (searchCriteria.name != null && !searchCriteria.name.equals("")) {
            Pattern pattern = Pattern.compile(searchCriteria.name, Pattern.CASE_INSENSITIVE);
            criterias.add(where("name").regex(pattern));
        }
        if (searchCriteria.orderedFrom != null) {
            criterias.add(where("orderedAt").gte(searchCriteria.orderedFrom));
        }
        if (searchCriteria.orderedTo != null) {
            criterias.add(where("orderedAt").lte(searchCriteria.orderedTo));
        }
        return new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
    }
}
