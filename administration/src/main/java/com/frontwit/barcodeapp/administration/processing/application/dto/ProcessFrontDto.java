package com.frontwit.barcodeapp.administration.processing.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessFrontDto {
    private Long barcode;
    private Integer status;
    private LocalDateTime dateTime;
}
