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
import java.util.regex.Pattern;

import static java.lang.String.format;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

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

    private void applyCriteria(Query query, OrderSearchCriteria criteria) {
        if (isNotEmpty(criteria.getName())) {
            var c = Criteria.where("name").regex(format("^%s", criteria.getName()), "i");
            query.addCriteria(c);
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
    }
//    private Criteria applyCriteria(OrderSearchCriteria criteria) {
//        List<Criteria> criterias = new ArrayList<>();
//        if (isNotEmpty(criteria.getName())) {
//            Pattern pattern = Pattern.compile(criteria.getName(), CASE_INSENSITIVE);
//            criterias.add(where("name").regex(pattern));
//        }
//        query.addCriteria(Criteria.where("tagName").regex(tagName));
////        if (criteria.getName() != null) {
////            criterias.add(where("name").is(criteria.getName()));
////        }
////        if (isNotEmpty(criteria.getColor())) {
////            Pattern pattern = Pattern.compile(criteria.getColor(), CASE_INSENSITIVE);
////            criterias.add(where("color").regex(pattern));
////        }
////        if (isNotEmpty(criteria.getCutter())) {
////            Pattern pattern = Pattern.compile(criteria.getCutter(), CASE_INSENSITIVE);
////            criterias.add(where("cutter").regex(pattern));
////        }
//        return new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
//    }

    private boolean isNotEmpty(String arg) {
        return arg != null && !"".equals(arg);
    }
}

interface OrderRepository extends MongoRepository<Order, Long> {

}
