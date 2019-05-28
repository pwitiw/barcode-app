package com.frontwit.barcodeapp.domain.order.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProcessCommand {
    private Long barcode;
    private int stage;
    private LocalDateTime date;

    public ProcessCommand(Long barcode, int stage, LocalDateTime date) {
        this.barcode = barcode;
        this.stage = stage;
        this.date = date;
    }
}
