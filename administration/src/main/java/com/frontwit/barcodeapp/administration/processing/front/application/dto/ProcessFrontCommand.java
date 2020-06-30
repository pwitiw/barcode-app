package com.frontwit.barcodeapp.administration.processing.front.application.dto;

import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessFrontCommand {
    @NonNull
    private Barcode barcode;
    @NonNull
    private Integer stage;
    private LocalDateTime dateTime;

    @Override
    public String toString() {
        return "ProcessFrontCommand{"
                + "barcode=" + barcode
                + ", stage=" + stage
                + ", dateTime=" + dateTime
                + '}';
    }
}
