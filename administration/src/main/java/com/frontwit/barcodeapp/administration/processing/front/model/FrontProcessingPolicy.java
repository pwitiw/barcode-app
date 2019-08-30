package com.frontwit.barcodeapp.administration.processing.front.model;

import java.util.Arrays;
import java.util.List;

import static com.frontwit.barcodeapp.administration.processing.front.model.Stage.IN_DELIVERY;
import static java.lang.String.format;

interface FrontProcessingPolicy {

    static FrontProcessingPolicy allPolicies() {
        return new CompositeFrontProcessingPolicy(new ProcessingIsNotCompleted(), new TimeGapeBetweenProcessesIsPreserved(), new ProcessingOrderIsPreserved());
    }

    void verify(ProcessedFront processedFront, ProcessingDetails details);
}

class TimeGapeBetweenProcessesIsPreserved implements FrontProcessingPolicy {

    @Override
    public void verify(ProcessedFront processedFront, ProcessingDetails details) {
        if (processedFront.containsDuplicates(details)) {
            throw new FrontProcessingException(format("Duplicated processing: barcode %s stage %s timestamp %s", processedFront.getBarcode(), details.getStage(), details.getDateTime()));
        }
    }
}

class ProcessingIsNotCompleted implements FrontProcessingPolicy {
    @Override
    public void verify(ProcessedFront processedFront, ProcessingDetails details) {
        if (processedFront.getCurrentStage() == IN_DELIVERY) {
            throw new FrontProcessingException(format("Front with barcode %s is already in delivery", processedFront.getBarcode()));
        }
    }
}

class ProcessingOrderIsPreserved implements FrontProcessingPolicy {
    @Override
    public void verify(ProcessedFront processedFront, ProcessingDetails details) {
        if (details.getStage().difference(processedFront.getCurrentStage()) > 1)
            throw new FrontProcessingException(format("Bad processing order: stage -> %s, applied stage -> %s", processedFront.getCurrentStage(), details.getStage()));
    }
}

class CompositeFrontProcessingPolicy implements FrontProcessingPolicy {

    private final List<FrontProcessingPolicy> policies;

    CompositeFrontProcessingPolicy(FrontProcessingPolicy... policies) {
        this.policies = Arrays.asList(policies);
    }

    @Override
    public void verify(ProcessedFront processedFront, ProcessingDetails details) {
        policies.forEach(policy -> policy.verify(processedFront, details));
    }
}

class FrontProcessingException extends IllegalStateException {
    FrontProcessingException(String msg) {
        super(msg);
    }
}