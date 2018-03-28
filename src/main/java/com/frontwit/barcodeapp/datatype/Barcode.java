package com.frontwit.barcodeapp.datatype;

import lombok.*;

import java.math.BigInteger;

@Getter
@AllArgsConstructor
public class Barcode {

    private BigInteger value;

    public static Barcode valueOf(long value) {
        return new Barcode(BigInteger.valueOf(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
