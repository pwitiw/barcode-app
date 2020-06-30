package com.frontwit.barcodeapp.administration.processing.synchronization.infrastructure;

import com.frontwit.barcodeapp.administration.processing.synchronization.SynchronizationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static java.time.ZoneOffset.UTC;

@AllArgsConstructor
@Service
public class SynchronizationRepositoryImpl implements SynchronizationRepository {

    private MongoSynchronizationRepository repository;

    @Override
    public Instant getLastSynchronizationDate() {
        return find().getDate();
    }

    @Override
    public void updateSyncDate() {
        var entity = find();
        entity.setDate(Instant.now());
        repository.save(entity);
    }

    private SynchronizationEntity find() {
        var entities = repository.findAll();
        if (entities.isEmpty()) {
            var entity = new SynchronizationEntity(UUID.randomUUID(), LocalDate.of(2000, 1, 1).atStartOfDay(UTC).toInstant());
            repository.save(entity);
            return entity;
        }
        return entities.get(0);
    }
}

interface MongoSynchronizationRepository extends MongoRepository<SynchronizationEntity, UUID> {
}
