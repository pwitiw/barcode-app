package com.frontwit.barcodeapp.administration.processing.synchronization.infrastructure;

import com.frontwit.barcodeapp.administration.processing.synchronization.SynchronizationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Service
public class SynchronizationRepositoryImpl implements SynchronizationRepository {

    private MongoSynchronizationRepository repository;

    public LocalDate getLastSynchronizationDate() {
        return find().getDate();
    }

    @Override
    public void updateSyncDate() {
        var entity = find();
        entity.setDate(LocalDate.now());
        repository.save(entity);
    }

    private SynchronizationEntity find() {
        var entities = repository.findAll();
        if (entities.isEmpty()) {
            var entity = new SynchronizationEntity(UUID.randomUUID(), LocalDate.of(2000, 1, 1));
            repository.save(entity);
            return entity;
        }
        return entities.get(0);
    }
}

interface MongoSynchronizationRepository extends MongoRepository<SynchronizationEntity, UUID> {
}
