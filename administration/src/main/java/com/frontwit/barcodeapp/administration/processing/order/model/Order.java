package com.frontwit.barcodeapp.administration.processing.order.model;

import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.processing.shared.OrderId;
import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;

@AllArgsConstructor
public class Order {

    @Getter
    private OrderId orderId;
    private Map<Barcode, Stage> fronts;
    @Getter
    private Stage stage = Stage.INIT;
    @Getter
    private boolean isCompleted;
    @Getter
    private LocalDate lastProcessedOn;

    private UpdateStagePolicy policy;

    public Order(OrderId orderId, UpdateStagePolicy policy) {
        this.orderId = orderId;
        this.policy = policy;
    }

    public void update(UpdateStageDetails details) {
        policy.verify(this, details.getBarcode());
        updateStage(details.getStage());
        isCompleted = Stage.isLast(stage) || isCompleted;
    }

    private void updateStage(Stage newStage) {
        if (newStage.isFurtherStage(stage)) {
            stage = newStage;
        }
        lastProcessedOn = LocalDate.now();
    }
}
