package com.frontwit.barcodeapp.administration.order.processing.front.model;

import java.util.Arrays;
import java.util.List;

import static com.frontwit.barcodeapp.administration.order.processing.shared.Stage.IN_DELIVERY;
import static java.lang.String.format;

interface FrontProcessingPolicy {

    static FrontProcessingPolicy allPolicies() {
        return new CompositeFrontProcessingPolicy(new ProcessingIsNotCompleted(), new TimeGapeBetweenProcessesIsPreserved(), new ProcessingOrderIsPreserved());
    }

    void verify(Front front, ProcessingDetails details);
}

class TimeGapeBetweenProcessesIsPreserved implements FrontProcessingPolicy {

    @Override
    public void verify(Front front, ProcessingDetails details) {
        if (front.containsDuplicates(details)) {
            throw new FrontProcessingException(format("Duplicated process: barcode %s stage %s timestamp %s", front.getBarcode().getBarcode(), details.getStage(), details.getDateTime()));
        }
    }
}

class ProcessingIsNotCompleted implements FrontProcessingPolicy {
    @Override
    public void verify(Front front, ProcessingDetails details) {
        if (front.getCurrentStage() == IN_DELIVERY) {
            throw new FrontProcessingException(format("Front with barcode %s is already in delivery", front.getBarcode()));
        }
    }
}

class ProcessingOrderIsPreserved implements FrontProcessingPolicy {
    @Override
    public void verify(Front front, ProcessingDetails details) {
        if (details.getStage().difference(front.getCurrentStage()) > 1)
            throw new FrontProcessingException(format("Bad model order: stage -> %s, applied stage -> %s", front.getCurrentStage(), details.getStage()));
    }
}

class CompositeFrontProcessingPolicy implements FrontProcessingPolicy {

    private final List<FrontProcessingPolicy> policies;

    CompositeFrontProcessingPolicy(FrontProcessingPolicy... policies) {
        this.policies = Arrays.asList(policies);
    }

    @Override
    public void verify(Front front, ProcessingDetails details) {
        policies.forEach(policy -> policy.verify(front, details));
    }
}

class FrontProcessingException extends IllegalStateException {
    FrontProcessingException(String msg) {
        super(msg);
    }
}