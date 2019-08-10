package com.frontwit.barcodeapp.administration.application.synchronization;

enum Symbol {
    NR("nr"),
    LENGTH("l"),
    WIDTH("w"),
    QUANTITY("q"),
    ELEMENT("el"),
    CUTTER("cu"),
    SIZE("si"),
    SIDE("do"),
    COLOR("co"),
    COMMENT("com");

    private String value;

    Symbol(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
