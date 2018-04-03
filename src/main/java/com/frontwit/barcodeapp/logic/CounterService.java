package com.frontwit.barcodeapp.logic;

import com.frontwit.barcodeapp.entity.Counter;
import com.frontwit.barcodeapp.entity.Order;
import com.frontwit.barcodeapp.repository.CounterRepository;

import javax.annotation.PostConstruct;

public class CounterService {

    private final static int MAX_COMPONENT_AMOUNT = 1000;

    private CounterRepository counterRepository;

    public CounterService(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    /**
     * This method gets the actual {@link Order} ID and increase it.
     *
     * @return increased ID which will be used as Order ID for new Entity.
     */
    public Long getNextOrderId() {
        Counter orderIdCounter = counterRepository.findOne("order");
        orderIdCounter.setValue(orderIdCounter.getValue() + MAX_COMPONENT_AMOUNT);
        counterRepository.save(orderIdCounter);
        return orderIdCounter.getValue();
    }
}
