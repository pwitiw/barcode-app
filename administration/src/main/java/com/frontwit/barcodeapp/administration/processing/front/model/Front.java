package com.frontwit.barcodeapp.administration.processing.front.model;

import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.processing.shared.Quantity;
import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Stream;

import static java.lang.String.format;
import static lombok.AccessLevel.PROTECTED;

public class Front {
    private static final Logger LOGGER = LoggerFactory.getLogger(Front.class);

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
    private List<DomainEvent> eventsToProcess = new ArrayList<>();

    Front(Barcode barcode, Quantity quantity, FrontProcessingPolicy policy) {
        this.barcode = barcode;
        this.quantity = quantity;
        this.processings = new HashSet<>();
        this.amendments = new HashSet<>();
        this.policy = policy;
    }

    public Front(Barcode barcode, Quantity quantity, Stage currentStage, Set<ProcessingDetails> processings, Set<ProcessingDetails> amendments, FrontProcessingPolicy policy) {
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
        if (!details.getStage().equals(this.currentStage)) {
            eventsToProcess.add(stageChanged(details));
        } else {
            LOGGER.info(format("PROCESSING {barcode=%s, stage=%s}", barcode, details.getStage()));
        }
        if (isPacked()) {
            eventsToProcess.add(new FrontPacked(barcode));
        }
    }

    private boolean isPacked() {
        return processings.stream()
                .map(ProcessingDetails::getStage)
                .filter(s -> s.equals(Stage.PACKING))
                .count() == quantity.getValue();
    }

    private void amend(ProcessingDetails details) {
        amendments.add(details);
        LOGGER.info(format("AMENDMENT {front=%s, stage=%s}", barcode, details.getStage()));
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

    private StageChanged stageChanged(ProcessingDetails details) {
        currentStage = details.getStage();
        return new StageChanged(barcode, details.getStage());
    }
}
