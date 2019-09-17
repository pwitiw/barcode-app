package com.frontwit.barcodeapp.administration.order.processing.front.infrastructure.persistence;

import com.frontwit.barcodeapp.administration.order.processing.front.model.Front;
import com.frontwit.barcodeapp.administration.order.processing.front.model.FrontRepository;
import com.frontwit.barcodeapp.administration.order.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.order.processing.synchronization.SaveSynchronizedFronts;
import com.frontwit.barcodeapp.administration.order.processing.synchronization.TargetFront;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class MongoFrontRepository implements FrontRepository, SaveSynchronizedFronts {

    private MongoTemplate mongoTemplate;

    @Override
    public void save(Front front) {
//        mongoTemplate.save(front);
    }

    @Override
    public Optional<Front> findBy(Barcode barcode) {
        return Optional.empty();
    }

    @Override
    public void save(List<TargetFront> fronts) {
        fronts.stream()
                .map(FrontEntity::new)
                .forEach(mongoTemplate::save);
    }
}
