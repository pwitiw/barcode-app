package com.frontwit.barcodeapp.administration.route.planning.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeliveryOrderDto {
    private String name;
    private int quantity;
    private double price;
    private boolean isSelected;

    public void setIsSelected(boolean selected) {
        isSelected = selected;
    }
}
