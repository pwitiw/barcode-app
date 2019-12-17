package com.frontwit.barcodeapp.administration.processing.front.model;

import com.frontwit.barcodeapp.administration.processing.shared.Barcode;

import java.util.Optional;

public interface FrontRepository {
    void save(Front front);

    Optional<Front> findBy(Barcode barcode);
}
