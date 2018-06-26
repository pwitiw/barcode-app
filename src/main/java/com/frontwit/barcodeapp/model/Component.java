package com.frontwit.barcodeapp.model;

import com.frontwit.barcodeapp.datatype.Barcode;
import com.frontwit.barcodeapp.datatype.Process;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;
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

    private Date lastModification;

    private boolean damaged;

    @Override
    public String toString() {
        return height + " x " + width;
    }
}