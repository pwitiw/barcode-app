package com.frontwit.barcodeapp.synchronization;

import com.frontwit.barcodeapp.logic.OrderService;

import javax.persistence.EntityManager;

public class SynchronizationService {

    EntityManager entityManager;

    OrderService orderService;


    public SynchronizationService(EntityManager entityManager, OrderService orderService) {
        this.entityManager = entityManager;
        this.orderService = orderService;
    }
}
