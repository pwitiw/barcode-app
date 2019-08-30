package com.frontwit.barcodeapp.administration.processing.front.model;

import lombok.NonNull;
import lombok.Value;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Value
public class ProcessingDetails {
    @NonNull Stage stage;
    @NonNull LocalDateTime dateTime;

    boolean equalsWithTimeAccuracy(ProcessingDetails details, long seconds) {
        return stage.equals(details.stage)
                && dateTime.until(details.dateTime, ChronoUnit.SECONDS) <= seconds;
    }
}

