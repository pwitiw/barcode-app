package com.frontwit.barcodeapp.logic;

import com.frontwit.barcodeapp.dao.repository.CounterRepository;
import com.frontwit.barcodeapp.model.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class BarcodeGeneratorService {

    public static final String BARCODE_ID = "barcode";
    public static final Long BARCODE_INIT_VALUE = 100000000L;
    public static final Long MAX_ORDER_AMOUNT = 1000L;

    private CounterRepository counterRepository;

    @Autowired
    public BarcodeGeneratorService(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    /**
     * This method returns barcode value and increments for next usage.
     *
     * @return new barcode.
     */
    public Long generate() {
        Counter barcodeCounter = counterRepository.findById(BARCODE_ID)
                .orElseThrow(IllegalArgumentException::new);
        Long value = barcodeCounter.getValue();
        barcodeCounter.setValue(barcodeCounter.getValue() + MAX_ORDER_AMOUNT);
        counterRepository.save(barcodeCounter);
        return value;
    }
}
