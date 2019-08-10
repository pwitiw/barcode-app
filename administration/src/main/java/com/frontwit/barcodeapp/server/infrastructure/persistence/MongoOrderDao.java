package com.frontwit.barcodeapp.server.infrastructure.persistence;

import com.frontwit.barcodeapp.server.application.order.Order;
import com.frontwit.barcodeapp.server.application.order.dto.OrderSearchCriteria;
import com.frontwit.barcodeapp.server.application.ports.OrderDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
        if (searchCriteria.empty()) {
            return repository.findAll(pageable);
        }
        return findOrdersForCriteria(pageable, searchCriteria);
    }


    private Page<Order> findOrdersForCriteria(Pageable pageable, OrderSearchCriteria criteria) {
//        var query = new Query().with(pageable);
        var query =    new Query(where("name").regex(""));
        applyCriteria(query, criteria);
        var orders = mongoOps.find(query, Order.class);
        return PageableExecutionUtils.getPage(orders, pageable, () -> repository.count());
    }

    private void applyCriteria(Query query, OrderSearchCriteria criteria) {
        if (isNotEmpty(criteria.getName())) {
//            var pattern = MongoRegexCreator.INSTANCE.toRegularExpression(criteria.getName(), CONTAINING);
//            query.addCriteria(where("name").regex(pattern, "i"));
//            Query query = new Query(
//                    Criteria.where("name").is()
//                            .andOperator(
//                                    Criteria.where("createdDate").lt(endDate),
//                                    Criteria.where("createdDate").gte(startDate)
//                            )
//            );
//            query.addCriteria(Criteria.where("name").alike(Example.of(criteria.getName())));
            query.addCriteria(Criteria.where("name").regex("^8"));
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
