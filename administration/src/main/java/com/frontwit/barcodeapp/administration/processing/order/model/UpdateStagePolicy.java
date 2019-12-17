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
        if (!order.contains(barcode)) {
            throw new UpdateStageException(format("Order  with id %s does not contain front with barcode %s", order.getOrderId().getOrderId(), barcode.getBarcode()));
        }
    }
}


class UpdateStageException extends RuntimeException {
    UpdateStageException(String msg) {
        super(msg);
    }
}