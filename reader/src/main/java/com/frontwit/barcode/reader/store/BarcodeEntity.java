package com.frontwit.barcode.reader.store;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class BarcodeEntity {

    private UUID id;
    private Long barcode;
    private Integer readerId;
    private LocalDateTime date;
}
