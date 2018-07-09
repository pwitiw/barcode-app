package com.frontwit.barcodeapp.logic;

import com.frontwit.barcodeapp.dao.repository.CounterRepository;
import com.frontwit.barcodeapp.datatype.Barcode;
import com.frontwit.barcodeapp.model.Counter;

public final class BarcodeGenerator {

    public static final String BARCODE_ID = "barcode";
    public static final Long BARCODE_INIT_VALUE = 100000000L;

    private CounterRepository counterRepository;

    public BarcodeGenerator(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    /**
     * This method returns barcode value and increments for next usage.
     *
     * @return new barcode.
     */
    public Barcode generate() {
        Counter barcodeCounter = counterRepository.findOne(BARCODE_ID);
        Long value = barcodeCounter.getValue();
        barcodeCounter.setValue(barcodeCounter.getValue() + 1L);
        counterRepository.save(barcodeCounter);
        return Barcode.valueOf(value);
    }
}
