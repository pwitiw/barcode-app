package com.frontwit.barcodeapp.administration.statistics.domain.order;

import lombok.Value;

@Value
public final class Meters {
    public static final Meters ZERO = Meters.of(0);

    private final double value;

    private Meters(double value) {
        this.value = value;
    }

    public static Meters of(double value) {
        return new Meters(value);
    }

    public Meters plus(Meters arg) {
        return new Meters(this.value + arg.value);
    }
}
