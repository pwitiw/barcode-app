package com.frontwit.barcodeapp.administration.processing.domain;

import java.util.stream.Stream;

public enum Status {

    INIT(0),        /* przed obrobka*/
    MILLING(1),     /* Frezowanie */
    POLISHING(2),   /* Czyszczenie */
    BASE(3),        /* Podkładowanie */
    GRINDING(4),    /* Szlifowanie */
    PAINTING(5),    /* Lakierowanie */
    PACKING(6),     /* Pakowanie */
    IN_DELIVERY(7); /* W dostarczeniu */
//    DELIVERD(8);    /* Dostarczone */

    private int id;

    public int getId() {
        return id;
    }

    Status(Integer id) {
        this.id = id;
    }

    public int difference(Status status) {
        return this.id - status.getId();
    }

    public static Status valueOf(int readerId) {
        return Stream.of(Status.values())
                .filter(value -> value.id == readerId)
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}

