package com.frontwit.barcodeapp.administration.processing.synchronization.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Document(collection = "synchronization")
public class SynchronizationEntity {

    @Id
    private UUID id;
    @Setter
    private Instant date;
}
