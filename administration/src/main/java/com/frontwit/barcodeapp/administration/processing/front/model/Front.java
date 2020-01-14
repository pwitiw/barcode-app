package com.frontwit.barcodeapp.administration.processing.front.model;

import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.processing.shared.Quantity;
import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.lang.String.format;

@AllArgsConstructor
public class Front {
    private static final Logger LOGGER = LoggerFactory.getLogger(Front.class);

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
        LOGGER.debug(format("PROCESSING: front %s on stage %s", barcode, details.getStage()));
        return Optional.empty();
    }

    private Optional<StageChanged> amend(ProcessingDetails details) {
        amendments.add(details);
        LOGGER.debug(format("AMENDMENT: front %s on stage %s", barcode, details.getStage()));
        return Optional.empty();
    }

    private boolean isProcess(ProcessingDetails details) {
        var processedQuantity = processings.stream()
                .filter(p -> p.getStage() == details.getStage())
                .count();
        var processedAtPreviousStageQuantity = processings.stream()
                .filter(p -> p.getStage() == Stage.valueOf(details.getStage().getId() - 1))
                .count();
        // fixme right now there is only one stage in production environment
//        var processable = processedQuantity == 0 || processedAtPreviousStageQuantity - processedQuantity > 0;
//        return !processingCompletedAt(details.getStage()) && processable;
        return !processingCompletedAt(details.getStage());
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
