package com.frontwit.barcodeapp.administration.infrastructure.db.mongo;

import com.frontwit.barcodeapp.administration.infrastructure.db.CustomerEntity;
import com.frontwit.barcodeapp.administration.infrastructure.db.CustomerRepository;
import com.frontwit.barcodeapp.administration.processing.synchronization.SourceCustomer;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Optional;

@AllArgsConstructor
public class MongoCustomerRepository implements CustomerRepository {
    private MongoTemplate mongoTemplate;

    @Override
    public Optional<CustomerEntity> findBy(Long id) {
        return Optional.ofNullable(mongoTemplate.findById(id, CustomerEntity.class));
    }

    @Override
    public void save(SourceCustomer customer) {
        mongoTemplate.save(new CustomerEntity(customer));
    }
}
