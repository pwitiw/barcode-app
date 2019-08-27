package com.frontwit.barcodeapp.administration.processing.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
class FrontProcessed {

    Barcode barcode;
    Status status;
    LocalDateTime dateTime;
}
