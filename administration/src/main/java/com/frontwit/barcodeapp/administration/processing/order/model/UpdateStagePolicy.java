package com.frontwit.barcodeapp.administration.processing.order.model;

import com.frontwit.barcodeapp.administration.processing.shared.Barcode;

import java.util.Collections;

import static java.lang.String.format;

public interface UpdateStagePolicy {

    void verify(Order order, Barcode barcode);

    static UpdateStagePolicy allPolicies() {
        return (Order order, Barcode barcode) ->
                Collections.singletonList(new FrontBelongsToOrder()).forEach(policy -> policy.verify(order, barcode));
    }
}

class FrontBelongsToOrder implements UpdateStagePolicy {

    @Override
    public void verify(Order order, Barcode barcode) {
        if (!order.getOrderId().equals(barcode.getOrderId())) {
            throw new UpdateStageException(
                    format("Barcode %s does not belong to order %s", barcode.getValue(), order.getOrderId().getId())
            );
        }
    }
}

class UpdateStageException extends RuntimeException {
    UpdateStageException(String msg) {
        super(msg);
    }
}
