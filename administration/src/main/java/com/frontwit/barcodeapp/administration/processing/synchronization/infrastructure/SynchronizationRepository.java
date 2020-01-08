package com.frontwit.barcodeapp.administration.processing.synchronization.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface SynchronizationRepository extends MongoRepository<SynchronizationEntity, UUID> {

    SynchronizationEntity findTopByOrderByIdDesc();

    @Override
    <S extends SynchronizationEntity> S save(S entity);
}
