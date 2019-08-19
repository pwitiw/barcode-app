package com.frontwit.barcodeapp.administration.infrastructure.persistence;

import com.frontwit.barcodeapp.administration.application.order.Order;
import com.frontwit.barcodeapp.administration.application.order.dto.OrderSearchCriteria;
import com.frontwit.barcodeapp.administration.application.ports.OrderDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;

@Repository
public class MongoOrderDao implements OrderDao {

    @PostConstruct
    public void test() {

        var order = Order.builder()
                .comment("hehe")
                .name("")
                .id(1L)
                .orderedAt(LocalDate.now())
                .components(Set.of())
                .build();
        repository.save(order);
    }

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
        if (searchCriteria.empty()) {
            return repository.findAll(pageable);
        }
        return findOrdersForCriteria(pageable, searchCriteria);
    }


    private Page<Order> findOrdersForCriteria(Pageable pageable, OrderSearchCriteria criteria) {
        var query = new Query().with(pageable);
        applyCriteria(query, criteria);
        var orders = mongoOps.find(query, Order.class);
        return PageableExecutionUtils.getPage(orders, pageable, () -> repository.count());
    }

    private void applyCriteria(Query query, OrderSearchCriteria searchCriteria) {
        var criteria = new Criteria();
        if (isNotEmpty(searchCriteria.getName())) {
            criteria.and("name").regex(format("^%s", searchCriteria.getName()), "i");
        }
        if (searchCriteria.getCompleted() != null && searchCriteria.getCompleted()) {
            criteria.and("isCompleted").is(true);
        } else {
            criteria.and("isCompleted").is(false);
        }
        query.addCriteria(criteria);
    }

    private boolean isNotEmpty(String arg) {
        return arg != null && !"".equals(arg);
    }
}

interface OrderRepository extends MongoRepository<Order, Long> {

}
