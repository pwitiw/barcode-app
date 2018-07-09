package com.frontwit.barcodeapp.dto;

import com.frontwit.barcodeapp.datatype.Stage;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderSearchCriteria {

    private String name;

    private Stage stage;

    private LocalDate from;

    private LocalDate to;

    public boolean isEmpty() {
        return name == null || name.equals("")
                && stage == null
                && from == null
                && to == null;
    }
}
