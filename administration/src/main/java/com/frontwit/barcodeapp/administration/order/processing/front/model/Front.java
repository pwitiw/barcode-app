package com.frontwit.barcodeapp.administration.order.processing.front.model;

import com.frontwit.barcodeapp.administration.order.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.order.processing.shared.Quantity;
import com.frontwit.barcodeapp.administration.order.processing.shared.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@AllArgsConstructor
public class Front {

    @Getter
    private Barcode barcode;
    private Quantity quantity;
    @Getter
    private Stage currentStage = Stage.INIT;
    @Getter
    private Set<ProcessingDetails> processings = new HashSet<>();
    @Getter
    private Set<ProcessingDetails> amendments = new HashSet<>();
    private FrontProcessingPolicy policy;

    Front(Barcode barcode, Quantity quantity, FrontProcessingPolicy policy) {
        this.barcode = barcode;
        this.quantity = quantity;
        this.policy = policy;
    }

    public Optional<FrontEvent.StageChanged> apply(ProcessingDetails details) {
        policy.verify(this, details);
        if (processingCompletedAt(details.getStage())) {
            return amend(details);
        }
        return process(details);
    }

    private Optional<FrontEvent.StageChanged> process(ProcessingDetails details) {
        processings.add(details);
        if (processingCompletedAt(details.getStage())) {
            return Optional.of(statusUpdated(details));
        }
        return Optional.empty();
    }

    private Optional<FrontEvent.StageChanged> amend(ProcessingDetails details) {
        amendments.add(details);
        if (details.getStage() != currentStage) {
            return Optional.of(statusUpdated(details));
        }
        return Optional.empty();
    }

    private boolean processingCompletedAt(Stage stage) {
        var processedQuantity = processings.stream()
                .filter(p -> p.getStage() == stage)
                .count();
        return quantity.equals(new Quantity((int) processedQuantity));
    }

    boolean containsDuplicates(ProcessingDetails details) {
        return Stream.concat(processings.stream(), amendments.stream())
                .anyMatch(detail -> detail.equalsWithTimeAccuracy(details, 3));
    }

    private FrontEvent.StageChanged statusUpdated(ProcessingDetails details) {
        currentStage = details.getStage();
        return new FrontEvent.StageChanged(barcode, details.getStage());
    }
}
