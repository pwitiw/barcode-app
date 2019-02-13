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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static org.springframework.data.mongodb.core.query.Criteria.where;


public class MongoOrderDao implements OrderDao {

    private OrderRepository repository;
    private MongoOperations mongoOps;


    public MongoOrderDao(OrderRepository repository, MongoOperations mongoOps) {
        this.repository = repository;
        this.mongoOps = mongoOps;
    }

    @Override
    public Order findOne(Long barcode) {
        return repository.findByBarcode(barcode)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Order save(Order order) {
        return repository.save(order);
    }

    @Override
    public Collection<Order> findByBarcodes(Set<Long> barcodes) {
        return repository.findByBarcodeIn(barcodes);
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Iterable<Order> save(Collection<Order> orders) {
        return repository.saveAll(orders);
    }

    @Override
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

        return PageableExecutionUtils.getPage(orders, pageable, () -> repository.count());
    }

    private Criteria getAppliedCriteria(OrderSearchCriteria searchCriteria) {
        List<Criteria> criterias = new ArrayList<>();
        if (searchCriteria.barcode != null) {
            criterias.add(where("_id").is(searchCriteria.barcode));
        }
        if (isNotEmpty(searchCriteria.name)) {
            Pattern pattern = Pattern.compile(searchCriteria.name, Pattern.CASE_INSENSITIVE);
            criterias.add(where("name").regex(pattern));
        }
        if (isNotEmpty(searchCriteria.color)) {
            Pattern pattern = Pattern.compile(searchCriteria.color, Pattern.CASE_INSENSITIVE);
            criterias.add(where("color").regex(pattern));
        }
        if (isNotEmpty(searchCriteria.cutter)) {
            Pattern pattern = Pattern.compile(searchCriteria.cutter, Pattern.CASE_INSENSITIVE);
            criterias.add(where("cutter").regex(pattern));
        }
        return new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
    }

    private boolean isNotEmpty(String arg) {
        return arg != null && !"".equals(arg);
    }
}
