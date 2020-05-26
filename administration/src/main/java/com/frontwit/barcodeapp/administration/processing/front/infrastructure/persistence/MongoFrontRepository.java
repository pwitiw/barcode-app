package com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence;

import com.frontwit.barcodeapp.administration.processing.front.model.Front;
import com.frontwit.barcodeapp.administration.processing.front.model.FrontProcessingPolicy;
import com.frontwit.barcodeapp.administration.processing.front.model.FrontRepository;
import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.processing.synchronization.TargetFront;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class MongoFrontRepository implements FrontRepository {

    private MongoTemplate mongoTemplate;
    private FrontProcessingPolicy processingPolicy;

    @Override
    public void save(Front front) {
        findById(front.getBarcode().getBarcode())
                .ifPresent(entity -> {
                    entity.update(front);
                    mongoTemplate.save(entity);
                });
    }

    @Override
    public Optional<Front> findBy(Barcode barcode) {
        return findById(barcode.getBarcode())
                .map(entity -> entity.toDomainModel(processingPolicy));
    }

    @Override
    public void save(List<TargetFront> fronts) {
        fronts.stream()
                .map(FrontEntity::new)
                .forEach(mongoTemplate::save);
    }

    private Optional<FrontEntity> findById(Long id) {
        return Optional.ofNullable(mongoTemplate.findById(id, FrontEntity.class));
    }
}
