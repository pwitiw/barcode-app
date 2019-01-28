package com.frontwit.barcodeapp.rest;

import com.frontwit.barcodeapp.dao.OrderDao;
import com.frontwit.barcodeapp.model.Order;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//TODO remove
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


        orderDao.save(Lists.newArrayList(order, order2));
    }

    // TODO usunac potem
    @PostMapping("/save")
    public void saveOrder(@RequestBody List<Order> orders) {
        orderDao.save(orders);
    }

}
