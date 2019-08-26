package com.frontwit.barcodeapp.administration.application.order;

import java.util.stream.Stream;

enum Stage {
    MILLING(1),     /* Frezowanie */
    POLISHING(2),   /* Czyszczenie */
    BASE(3),        /* Podkładowanie */
    GRINDING(4),    /* Szlifowanie */
    PAINTING(5),    /* Lakierowanie */
    PACKING(6),     /* Pakowanie */
    IN_DELIVERY(7), /* W dostarczeniu */
    DELIVERD(8);    /* Dostarczone */

    private int id;

    public int getId() {
        return id;
    }

    Stage(Integer id) {
        this.id = id;
    }

    public int difference(Stage stage) {
        return this.id - stage.getId();
    }

    public static Stage valueOf(int readerId) {
        return Stream.of(Stage.values())
                .filter(value -> value.id == readerId)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
