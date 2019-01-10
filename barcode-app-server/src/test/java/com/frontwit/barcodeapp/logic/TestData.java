package com.frontwit.barcodeapp.logic;

import com.frontwit.barcodeapp.model.Component;
import com.frontwit.barcodeapp.model.Order;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TestData {

    public static Order createOrder(Long barcode, Integer componentsAmount) {
        Set<Component> components = new HashSet<>();
        for (int i = 1; i <= componentsAmount; i++) {
            components.add(createComponent(barcode + i));
        }
        return createOrder(barcode, components);
    }

    public static Order createOrder(Long barcode, Set<Component> components) {
        return Order.builder()
                .id(new ObjectId())
                .barcode(barcode)
                .components(components)
                .build();
    }

    public static Component createComponent(Long barcode) {
        return Component.builder()
                .barcode(barcode)
                .processingHistory(new ArrayList<>())
                .build();
    }
}
