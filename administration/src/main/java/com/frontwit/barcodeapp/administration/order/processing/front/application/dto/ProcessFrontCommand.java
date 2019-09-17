package com.frontwit.barcodeapp.administration.order.processing.front.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProcessFrontCommand {
    @NonNull
    private Long barcode;
    @NonNull
    private Integer stage;
    private LocalDateTime dateTime;
}
