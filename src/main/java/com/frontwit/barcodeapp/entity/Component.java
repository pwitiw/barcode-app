package com.frontwit.barcodeapp.entity;

import com.frontwit.barcodeapp.datatype.Barcode;
import com.frontwit.barcodeapp.datatype.Process;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
public class Component {

    @Id
    private Long id;

    private Integer width;

    private Integer length;

    private Barcode barcode;

    private List<Process> processingHistory;

    private LocalTime lastModification;

    private boolean damaged;

}
