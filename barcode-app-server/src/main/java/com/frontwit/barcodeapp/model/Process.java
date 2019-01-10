package com.frontwit.barcodeapp.model;

import com.frontwit.barcodeapp.datatype.Stage;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@EqualsAndHashCode(of = "stage")
@NoArgsConstructor
@AllArgsConstructor
public class Process {

    private Stage stage;
    private LocalTime date;
}
