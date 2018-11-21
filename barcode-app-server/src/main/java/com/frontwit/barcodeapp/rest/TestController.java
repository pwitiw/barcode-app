package com.frontwit.barcodeapp.rest;

import com.frontwit.barcodeapp.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private OrderDao orderDao;

    @Autowired
    public void setOrderRepository(OrderDao orderDao) {
        this.orderDao = orderDao;
    }
}
