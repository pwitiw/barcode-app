package com.frontwit.barcodeapp.administration.order.processing.order.model;

import com.frontwit.barcodeapp.administration.order.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.order.processing.shared.OrderId;
import com.frontwit.barcodeapp.administration.order.processing.shared.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public class Order {

    @Getter
    private OrderId orderId;
    @Getter
    private Map<Barcode, Stage> fronts;
    @Getter
    private Stage stage = Stage.INIT;
    @Getter
    private boolean isCompleted;

    private UpdateStagePolicy policy;

    public Order(OrderId orderId, Map<Barcode, Stage> fronts, UpdateStagePolicy policy) {
        this.orderId = orderId;
        this.fronts = fronts;
        this.policy = policy;
    }

    public void update(UpdateStageDetails details) {
        policy.verify(this, details.getBarcode());
        fronts.put(details.getBarcode(), details.getStage());
        updateStage();
        isCompleted = Stage.isLast(stage);
        }

    boolean contains(Barcode barcode) {
        return fronts.containsKey(barcode);
    }

    private void updateStage() {
        stage = fronts.values().stream()
                .max(Stage::difference)
                .orElse(Stage.INIT);
    }
}
