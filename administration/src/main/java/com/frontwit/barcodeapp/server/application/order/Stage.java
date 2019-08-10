package com.frontwit.barcodeapp.server.application.order;

import java.util.stream.Stream;

import static org.springframework.util.Assert.notNull;

enum Stage {
    MILLING(1),
    POLISHING(2),
    BASE(3),
    GRINDING(4),
    PAINTING(5),
    PACKING(6),
    SENT(7),
    DELIVERD(8);

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
