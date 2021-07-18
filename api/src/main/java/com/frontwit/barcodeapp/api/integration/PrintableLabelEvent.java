package com.frontwit.barcodeapp.api.integration;

import com.frontwit.barcodeapp.api.shared.Stage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrintableLabelEvent implements IntegrationEvent {
    private Long orderId;
    private String orderName;
    private Stage stage;
    private int totalQuantity;
    private String route;
    private String customerName;
    private long barcode;
    private int height;
    private int width;
    private int frontsQuantity;
}
