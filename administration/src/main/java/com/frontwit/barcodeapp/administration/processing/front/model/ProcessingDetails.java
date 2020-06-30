package com.frontwit.barcodeapp.administration.processing.front.model;

import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Value
public class ProcessingDetails {
    @NonNull
    private final Stage stage;
    @NonNull
    private final LocalDateTime dateTime;

    boolean equalsWithTimeAccuracy(ProcessingDetails details, long seconds) {
        return stage.equals(details.stage)
                && dateTime.until(details.dateTime, ChronoUnit.SECONDS) <= seconds;
    }
}

