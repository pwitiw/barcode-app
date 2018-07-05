package com.frontwit.barcodeapp.model;

import com.frontwit.barcodeapp.datatype.Barcode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Component {

    @NotEmpty
    @Indexed(unique = true)
    private Barcode barcode;

    @NotEmpty
    private Integer height;

    @NotEmpty
    private Integer width;

    private List<Process> processingHistory;

    private LocalDate lastModification;

    private boolean damaged;

    @Override
    public String toString() {
        return height + " x " + width;
    }
}