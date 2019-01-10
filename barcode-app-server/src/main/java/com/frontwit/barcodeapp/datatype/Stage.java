package com.frontwit.barcodeapp.datatype;

import java.util.stream.Stream;

import static org.springframework.util.Assert.notNull;

public enum Stage {

    MILLING(1),
    POLISHING(2),
    BASE(3),
    GRINDING(4),
    PAINTING(5),
    PACKING(6),
    SENT(7),
    DELIVERD(8);

    private Integer readerId;

    public Integer getReaderId() {
        return readerId;
    }

    Stage(Integer readerId) {
        this.readerId = readerId;
    }

    public boolean greatherThan(Stage stage) {
        return this.readerId > stage.getReaderId();
    }

    public static Stage valueOf(Integer readerId) {
        notNull(readerId, "Stage can not be null");
        return Stream.of(Stage.values())
                .filter(value -> value.readerId.equals(readerId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }


}
