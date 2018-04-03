package com.frontwit.barcodeapp.datatype;

import java.util.Arrays;

public enum Stage {

    MILLING(1),
    POLISHING(2),
    BASE(3),
    GRINDING(4),
    PAINTING(5),
    PACKING(5),
    SENT(6),
    DELIVERD(7);

    private int positionNumber;

    Stage(int positionNumber) {
        this.positionNumber = positionNumber;
    }

    public static Stage valueOf(int positionNumber) {
        return Arrays.stream(values())
                .filter(value -> value.positionNumber == positionNumber)
                .findFirst()
                .orElse(null);
    }

}
