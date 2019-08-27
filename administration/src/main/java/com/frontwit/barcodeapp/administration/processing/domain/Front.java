package com.frontwit.barcodeapp.administration.processing.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Front {

    private UUID id;
    private Barcode barcode;
    private int quantity;

    public Front(UUID id, Barcode barcode, int quantity) {
        this.id = id;
        this.barcode = barcode;
        this.quantity = quantity;
    }

    @Getter
    private List<FrontProcessed> pendingEvents = new ArrayList<>();

    public void apply(ProcessFront command, ProcessingPolicy processingPolicy) {
        processingPolicy.verify();
        frontProcessed(new FrontProcessed(command.getBarcode(), command.getStatus(), command.getDateTime()));
    }

    public Status getStatus() {
        return Status.INIT;
    }

    public static Front recreateFrom(UUID id, Barcode barcode, int quantity, List<FrontProcessed> processes) {
        return new Front(null, null, 2);
    }

    private void frontProcessed(FrontProcessed frontProcessed) {
        pendingEvents.add(frontProcessed);
    }


    private void handle(DomainEvent event) {

    }

}