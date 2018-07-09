package com.frontwit.barcodeapp.model;

import com.frontwit.barcodeapp.datatype.Stage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Process {

    private Stage stage;

    private LocalTime date;

    @DBRef
    private Worker worker;

}
