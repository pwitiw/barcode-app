package com.frontwit.barcodeapp.administration.processing.front.model;

import com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence.FrontEntity;
import com.frontwit.barcodeapp.administration.processing.shared.Barcode;

import java.util.Optional;

public interface FrontStatisticsRepository {
    Optional<FrontEntity> findByBarcode(Barcode barcode);
}
