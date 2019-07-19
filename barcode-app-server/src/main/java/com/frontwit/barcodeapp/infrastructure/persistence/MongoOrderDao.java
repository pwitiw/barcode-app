package com.frontwit.barcodeapp.infrastructure.persistence;

import com.frontwit.barcodeapp.application.order.Order;
import com.frontwit.barcodeapp.application.order.dto.OrderSearchCriteria;
import com.frontwit.barcodeapp.application.ports.OrderDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class MongoOrderDao implements OrderDao {

    private MongoOperations mongoOps;
    private OrderRepository repository;

    public MongoOrderDao(MongoOperations mongoOps, OrderRepository repository) {
        this.mongoOps = mongoOps;
        this.repository = repository;
    }

    @Override
    public Optional<Order> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public Order save(Order order) {
        return repository.save(order);
    }

    @Override
    public Page<Order> find(Pageable pageable, OrderSearchCriteria searchCriteria) {
        if (searchCriteria == null || searchCriteria.empty()) {
            return repository.findAll(pageable);
        }
        return findOrdersForCriteria(pageable, searchCriteria);
    }


    private Page<Order> findOrdersForCriteria(Pageable pageable, OrderSearchCriteria criteria) {
        Criteria appliedCriterias = getAppliedCriteria(criteria);
        Query query = Query.query(appliedCriterias).with(pageable);
        List<Order> orders = mongoOps.find(query, Order.class);

        return PageableExecutionUtils.getPage(orders, pageable, () -> repository.count());
    }

    private Criteria getAppliedCriteria(OrderSearchCriteria criteria) {
        List<Criteria> criterias = new ArrayList<>();
        if (isNotEmpty(criteria.getName())) {
            Pattern pattern = Pattern.compile(criteria.getName(), CASE_INSENSITIVE);
            criterias.add(where("name").regex(pattern));
        }

//        if (criteria.getName() != null) {
//            criterias.add(where("name").is(criteria.getName()));
//        }
//        if (isNotEmpty(criteria.getColor())) {
//            Pattern pattern = Pattern.compile(criteria.getColor(), CASE_INSENSITIVE);
//            criterias.add(where("color").regex(pattern));
//        }
//        if (isNotEmpty(criteria.getCutter())) {
//            Pattern pattern = Pattern.compile(criteria.getCutter(), CASE_INSENSITIVE);
//            criterias.add(where("cutter").regex(pattern));
//        }
        return new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
    }

    private boolean isNotEmpty(String arg) {
        return arg != null && !"".equals(arg);
    }
}

interface OrderRepository extends MongoRepository<Order, Long> {

}
