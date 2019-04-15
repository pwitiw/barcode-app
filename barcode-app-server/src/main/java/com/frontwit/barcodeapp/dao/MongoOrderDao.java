package com.frontwit.barcodeapp.dao;

import com.frontwit.barcodeapp.domain.order.OrderSearchCriteria;
import com.frontwit.barcodeapp.domain.order.processing.Order;
import com.frontwit.barcodeapp.domain.order.processing.ports.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.Collection;
import java.util.Set;


public class MongoOrderDao implements OrderRepository {

    private MongoOperations mongoOps;


    public MongoOrderDao(MongoOperations mongoOps) {
        this.mongoOps = mongoOps;
    }

    @Override
    public Order findOne(Long barcode) {
//        return findByBarcode(barcode)
//                .orElseThrow(IllegalArgumentException::new);
        return null;
    }

    @Override
    public Order save(Order order) {
//        return repository.save(order);
        return null;
    }

    @Override
    public Collection<Order> findByBarcodes(Set<Long> barcodes) {
//        return repository.findByBarcodeIn(barcodes);
        return null;
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Iterable<Order> save(Collection<Order> orders) {
        return null;
    }

    @Override
    public Page<Order> findForCriteria(Pageable pageable, OrderSearchCriteria searchCriteria) {
        return null;
    }

//    @Override
//    public Page<Order> findAll(Pageable pageable) {
//        return repository.findAll(pageable);
//    }
//
//    @Override
//    public Iterable<Order> save(Collection<Order> orders) {
//        return repository.saveAll(orders);
//    }
//
//    @Override
//    public Page<Order> findForCriteria(Pageable pageable, OrderSearchCriteria searchCriteria) {
//        if (OrderSearchCriteria.isEmpty(searchCriteria)) {
//            return findAll(pageable);
//        }
//        return findOrdersForCriteria(pageable, searchCriteria);
//    }
//
//    private Page<Order> findOrdersForCriteria(Pageable pageable, OrderSearchCriteria searchCriteria) {
//        Criteria appliedCriterias = getAppliedCriteria(searchCriteria);
//        Query query = Query.query(appliedCriterias).with(pageable);
//        List<Order> orders = mongoOps.find(query, Order.class);
//
//        return PageableExecutionUtils.getPage(orders, pageable, () -> repository.count());
//    }
//
//    private Criteria getAppliedCriteria(OrderSearchCriteria searchCriteria) {
//        List<Criteria> criterias = new ArrayList<>();
//        if (searchCriteria.barcode != null) {
//            criterias.add(where("_id").is(searchCriteria.barcode));
//        }
//        if (isNotEmpty(searchCriteria.name)) {
//            Pattern pattern = Pattern.compile(searchCriteria.name, Pattern.CASE_INSENSITIVE);
//            criterias.add(where("name").regex(pattern));
//        }
//        if (isNotEmpty(searchCriteria.color)) {
//            Pattern pattern = Pattern.compile(searchCriteria.color, Pattern.CASE_INSENSITIVE);
//            criterias.add(where("color").regex(pattern));
//        }
//        if (isNotEmpty(searchCriteria.cutter)) {
//            Pattern pattern = Pattern.compile(searchCriteria.cutter, Pattern.CASE_INSENSITIVE);
//            criterias.add(where("cutter").regex(pattern));
//        }
//        return new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
//    }
//
//    private boolean isNotEmpty(String arg) {
//        return arg != null && !"".equals(arg);
//    }
}
