package com.frontwit.barcodeapp.administration.processing.front.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@AllArgsConstructor
class ProcessedFront implements Front {

    @Getter
    private Barcode barcode;
    private Quantity quantity;
    @Getter
    private Stage currentStage = Stage.INIT;
    private Set<ProcessingDetails> processings = new HashSet<>();
    private Set<ProcessingDetails> amendments = new HashSet<>();
    private FrontProcessingPolicy policy;

    ProcessedFront(Barcode barcode, Quantity quantity, FrontProcessingPolicy policy) {
        this.barcode = barcode;
        this.quantity = quantity;
        this.policy = policy;
    }

    public Optional<StageUpdated> apply(ProcessingDetails details) {
        policy.verify(this, details);
        if (processingCompletedAt(details.getStage())) {
            return amend(details);
        }
        return process(details);
    }

    private Optional<StageUpdated> process(ProcessingDetails details) {
        processings.add(details);
        if (processingCompletedAt(details.getStage())) {
            return statusUpdated(details);
        }
        return Optional.empty();
    }

    private Optional<StageUpdated> amend(ProcessingDetails details) {
        amendments.add(details);
        if (details.getStage() != currentStage) {
            return statusUpdated(details);
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

    private Optional<StageUpdated> statusUpdated(ProcessingDetails details) {
        currentStage = details.getStage();
        return Optional.of(new StageUpdated(barcode, details.getStage()));
    }
}
