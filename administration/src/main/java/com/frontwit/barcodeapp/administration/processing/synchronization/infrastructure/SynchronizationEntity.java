package com.frontwit.barcodeapp.administration.processing.synchronization.infrastructure;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class SynchronizationEntity {

    private UUID id;
    private LocalDateTime date;
}
