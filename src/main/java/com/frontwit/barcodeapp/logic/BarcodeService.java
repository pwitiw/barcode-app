package com.frontwit.barcodeapp.logic;

import com.frontwit.barcodeapp.dao.repository.CounterRepository;
import com.frontwit.barcodeapp.model.Counter;

public final class BarcodeService {

    public static final String BARCODE_ID = "barcode";
    public static final Long BARCODE_INIT_VALUE = 100000000L;
    static final Long MAX_ORDER_AMOUNT = 1000L;

    private CounterRepository counterRepository;

    public BarcodeService(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    /**
     * This method returns barcode value and increments for next usage.
     *
     * @return new barcode.
     */
    public Long generate() {
        Counter barcodeCounter = counterRepository.findOne(BARCODE_ID);
        Long value = barcodeCounter.getValue();
        barcodeCounter.setValue(barcodeCounter.getValue() + MAX_ORDER_AMOUNT);
        counterRepository.save(barcodeCounter);
        return value;
    }
}
