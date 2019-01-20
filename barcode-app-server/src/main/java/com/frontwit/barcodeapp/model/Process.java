package com.frontwit.barcodeapp.model;

import com.frontwit.barcodeapp.datatype.Stage;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = "stage")
@NoArgsConstructor
@AllArgsConstructor
public class Process {

    private Stage stage;
    private LocalDateTime date;
}
