package com.frontwit.barcodeapp.administration.statistics.domain.order;

import lombok.Value;

@Value
public final class Meters {
    public static final Meters ZERO = Meters.of(0);

    private final double meters;

    private Meters(double meters) {
        this.meters = meters;
    }

    public static Meters of(double val) {
        return new Meters(val);
    }

    public Meters plus(Meters arg) {
        return new Meters(this.meters + arg.meters);
    }
}
