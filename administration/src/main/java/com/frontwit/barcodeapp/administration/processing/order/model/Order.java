package com.frontwit.barcodeapp.administration.processing.order.model;

import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.processing.shared.OrderId;
import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.Set;

@AllArgsConstructor
public class Order {

    @Getter
    private final OrderId orderId;
    @Getter
    private final Set<Barcode> notPackedFronts;
    @Getter
    private Stage stage = Stage.INIT;
    @Getter
    private Instant lastProcessedOn = Instant.now();
    @Getter
    private boolean packed;

    private final UpdateStagePolicy policy;

    public Order(OrderId orderId, UpdateStagePolicy policy, Set<Barcode> notPackedFronts) {
        this.orderId = orderId;
        this.policy = policy;
        this.notPackedFronts = notPackedFronts;
    }

    public void update(UpdateStageDetails details) {
        policy.verify(this, details.getBarcode());
        updateStage(details.getStage());
    }

    public void pack(PackFront front) {
        notPackedFronts.remove(front.getBarcode());
        packed = notPackedFronts.isEmpty();
    }

    private void updateStage(Stage newStage) {
        if (newStage.isFurtherStage(stage)) {
            stage = newStage;
        }
        lastProcessedOn = Instant.now();
    }
}
