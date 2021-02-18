package com.frontwit.barcodeapp.administration.processing.front.model;

import com.frontwit.barcodeapp.administration.processing.shared.Stage;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

public interface FrontProcessingPolicy {

    static FrontProcessingPolicy allPolicies() {
        return new CompositeFrontProcessingPolicy(new ProcessingIsNotCompleted(), new TimeGapeBetweenProcessesIsPreserved());
    }

    void verify(Front front, ProcessingDetails details);
}

class TimeGapeBetweenProcessesIsPreserved implements FrontProcessingPolicy {


    @Override
    public void verify(Front front, ProcessingDetails details) {
        if (front.containsDuplicates(details)) {
            throw new ProcessingPolicyViolationException(
                    format(
                            "Duplicated process: {barcode=%s, stage=%s, timestamp=%s}",
                            front.getBarcode().getBarcode(), details.getStage(), details.getDateTime()
                    )
            );
        }
    }
}

class ProcessingIsNotCompleted implements FrontProcessingPolicy {
    @Override
    public void verify(Front front, ProcessingDetails details) {
        if (front.getCurrentStage() == Stage.IN_DELIVERY) {
            throw new ProcessingPolicyViolationException(format("Front {barcode=%s} is already in delivery", front.getBarcode()));
        }
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
