package com.frontwit.barcodeapp.application.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessCommand {
    private Long barcode;
    private Integer stage;
    private LocalDateTime date;
}
