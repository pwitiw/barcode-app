package com.frontwit.barcodeapp.datatype;

import lombok.Data;

import java.time.LocalTime;

@Data
public class Process {

    private Stage stage;

    private LocalTime time;

}
