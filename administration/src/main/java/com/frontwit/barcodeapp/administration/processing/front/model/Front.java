package com.frontwit.barcodeapp.administration.processing.front.model;

import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.processing.shared.Quantity;
import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;


public class Front {

    @Getter
    private final Barcode barcode;
    private final Quantity quantity;

    @Getter
    private final Set<ProcessingDetails> processings;
    @Getter
    private final Set<ProcessingDetails> amendments;
    private final FrontProcessingPolicy policy;

    @Getter
    private Stage currentStage = Stage.INIT;
    @Getter
    private final List<DomainEvent> eventsToProcess = new ArrayList<>();

    Front(Barcode barcode, Quantity quantity, FrontProcessingPolicy policy) {
        this.barcode = barcode;
        this.quantity = quantity;
        this.processings = new HashSet<>();
        this.amendments = new HashSet<>();
        this.policy = policy;
    }

    public Front(
            Barcode barcode,
            Quantity quantity,
            Stage currentStage,
            Set<ProcessingDetails> processings,
            Set<ProcessingDetails> amendments,
            FrontProcessingPolicy policy
    ) {
        this.barcode = barcode;
        this.quantity = quantity;
        this.processings = processings;
        this.amendments = amendments;
        this.policy = policy;
        this.currentStage = currentStage;
    }

    public List<DomainEvent> apply(ProcessingDetails details) {
        policy.verify(this, details);
        if (notCompletedAt(details.getStage())) {
            process(details);
        } else {
            amend(details);
        }
        return eventsToProcess;
    }

    private void process(ProcessingDetails details) {
        processings.add(details);
        eventsToProcess.add(new FrontProcessed(details.getStage(), details.getDateTime()));
        if (hasStageChanged(details)) {
            stageChanged(details);
        }
        if (isPacked()) {
            eventsToProcess.add(new FrontPacked(barcode, quantity));
        }
    }

    private boolean hasStageChanged(ProcessingDetails details) {
        return !details.getStage().equals(this.currentStage);
    }

    private boolean isPacked() {
        return processings.stream()
                .map(ProcessingDetails::getStage)
                .filter(s -> s.equals(Stage.PACKING))
                .count() == quantity.getValue();
    }

    private void amend(ProcessingDetails details) {
        amendments.add(details);
        eventsToProcess.add(new FrontAmended(barcode, details.getStage()));
    }

    private boolean notCompletedAt(Stage stage) {
        var processedQuantity = processings.stream()
                .filter(p -> p.getStage() == stage)
                .count();
        return !quantity.equals(new Quantity((int) processedQuantity));
    }

    boolean containsDuplicates(ProcessingDetails details) {
        return Stream.concat(processings.stream(), amendments.stream())
                .anyMatch(detail -> detail.equalsWithTimeAccuracy(details, 3));
    }

    private void stageChanged(ProcessingDetails details) {
        currentStage = details.getStage();
        eventsToProcess.add(new FrontStageChanged(barcode, currentStage));
    }
}
