package com.frontwit.barcodeapp.dao;

import com.frontwit.barcodeapp.dao.repository.OrderRepository;
import com.frontwit.barcodeapp.dto.OrderSearchCriteria;
import com.frontwit.barcodeapp.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class OrderDao {

    private OrderRepository repository;
    private MongoOperations mongoOps;

    public OrderDao(OrderRepository repository, MongoOperations mongoOps) {
        this.repository = repository;
        this.mongoOps = mongoOps;
    }

    public Page<Order> findAll(Pageable pageable, OrderSearchCriteria searchCriteria) {
        if (searchCriteria == null || searchCriteria.isEmpty()) {
            return repository.findAll(pageable);
        } else {
            return findOrdersForCriteria(pageable, searchCriteria);
        }
    }

    //TODO implement this method
    private Page<Order> findOrdersForCriteria(Pageable pageable, OrderSearchCriteria searchCriteria) {
        Criteria criterias = new Criteria();
        if (searchCriteria.getName() != null && !searchCriteria.getName().equals("")) {
            criterias.andOperator(where("name").is(searchCriteria.getName()));
        }
//        if (searchCriteria.getStage() != null) {
//        }

//            mongoOps.findOne(where("name").is(searchCriteria.getName()));
//        query = query(where("continent.name").is("Europe"));
        Query query = Query.query(criterias).with(pageable);
        List<Order> orders = mongoOps.find(query, Order.class);

        return PageableExecutionUtils.getPage(
                orders,
                pageable,
                () -> repository.count());
    }
}
