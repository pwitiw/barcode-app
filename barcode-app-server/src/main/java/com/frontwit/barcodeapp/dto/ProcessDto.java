package com.frontwit.barcodeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessDto {

    private Integer readerId;
    private Long barcode;
    private LocalDateTime date;


}
