package com.frontwit.barcodeapp.administration.processing.shared;

import java.util.stream.Stream;

public enum Stage {

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

    Stage(Integer id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int difference(Stage stage) {
        return this.getId() - stage.getId();
    }

    public static boolean isLast(Stage stage) {
        return stage == IN_DELIVERY;
    }

    public static Stage valueOf(int id) {
        return Stream.of(Stage.values())
                .filter(value -> value.id == id)
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}

