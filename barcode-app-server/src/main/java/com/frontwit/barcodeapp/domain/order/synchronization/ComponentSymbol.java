package com.frontwit.barcodeapp.domain.order.synchronization;

public enum ComponentSymbol {
    NR("nr"),
    LENGTH("l"),
    WIDTH("w"),
    QUANTITY("q"),
    AREA("a"),
    ELEMENT("el"),
    CUTTER("cu"),
    SIZE("si"),
    SIDE("do"),
    COLOR("co"),
    COMMENT("com");

    private String symbol;

    ComponentSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
