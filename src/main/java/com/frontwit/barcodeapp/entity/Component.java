package com.frontwit.barcodeapp.entity;

import com.frontwit.barcodeapp.datatype.Barcode;
import com.frontwit.barcodeapp.datatype.Process;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class Component {

    @NotEmpty
    @Indexed(unique = true)
    private Barcode barcode;
    @NotEmpty
    private Integer width;
    @NotEmpty
    private Integer length;

    private List<Process> processingHistory;

    private LocalTime lastModification;

    private boolean damaged;
}
