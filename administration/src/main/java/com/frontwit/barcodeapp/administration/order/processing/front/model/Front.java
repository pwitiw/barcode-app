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

    public Optional<StageChanged> apply(ProcessingDetails details) {
        policy.verify(this, details);
        if (isProcess(details)) {
            return process(details);
        }
        return amend(details);
    }

    private Optional<StageChanged> process(ProcessingDetails details) {
        processings.add(details);
        if (!details.getStage().equals(this.currentStage)) {
            return Optional.of(statusUpdated(details));
        }
        return Optional.empty();
    }

    private Optional<StageChanged> amend(ProcessingDetails details) {
        amendments.add(details);
        if (details.getStage() != currentStage) {
            return Optional.of(statusUpdated(details));
        }
        return Optional.empty();
    }

    private boolean isProcess(ProcessingDetails details) {
        var processedQuantity = processings.stream()
                .filter(p -> p.getStage() == details.getStage())
                .count();
        var processedAtPreviousStageQuantity = processings.stream()
                .filter(p -> p.getStage() == Stage.valueOf(details.getStage().getId() - 1))
                .count();
        var processable = processedQuantity == 0 || processedAtPreviousStageQuantity - processedQuantity > 0;
        return !processingCompletedAt(details.getStage()) && processable;
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

    private StageChanged statusUpdated(ProcessingDetails details) {
        currentStage = details.getStage();
        return new StageChanged(barcode, details.getStage());
    }
}
