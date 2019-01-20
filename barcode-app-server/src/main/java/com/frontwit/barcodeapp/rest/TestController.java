package com.frontwit.barcodeapp.rest;

import com.frontwit.barcodeapp.dao.OrderDao;
import com.frontwit.barcodeapp.model.Order;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    private OrderDao orderDao;

    @Autowired
    public void setOrderRepository(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @GetMapping
    public void test() {
        Order order = new Order();
        order.setExtId(1L);
        order.setName("dupa");
        order.setBarcode(15000L);

        Order order2 = new Order();
        order2.setName("dupa");
        order2.setExtId(1L);
        order2.setBarcode(20100L);


        Iterable<Order> save = orderDao.save(Lists.newArrayList(order, order2));
    }

}
