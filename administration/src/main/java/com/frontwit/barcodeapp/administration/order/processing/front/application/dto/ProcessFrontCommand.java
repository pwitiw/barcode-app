package com.frontwit.barcodeapp.administration.order.processing.front.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessFrontCommand {
    @NonNull
    private Long barcode;
    @NonNull
    private Integer stage;
    private LocalDateTime dateTime;
}
