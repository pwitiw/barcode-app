package com.frontwit.barcodeapp.rest;

import com.frontwit.barcodeapp.dao.OrderDao;
import com.frontwit.barcodeapp.datatype.Stage;
import com.frontwit.barcodeapp.model.Component;
import com.frontwit.barcodeapp.model.Order;
import com.frontwit.barcodeapp.model.Route;
import com.google.common.collect.Lists;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@RestController
public class TestController {

    private OrderDao orderDao;

    @Autowired
    public void setOrderRepository(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @GetMapping("/test")
    public void testData() {

        // Order 1
        Set<Component> components1 = new HashSet<>();
        components1.add(new Component(1L, 1, 1, Lists.newArrayList(), LocalDate.now(), true));
        components1.add(new Component(2L, 11, 22, Lists.newArrayList(), LocalDate.now(), true));
        components1.add(new Component(3L, 111, 111, Lists.newArrayList(), LocalDate.now(), true));
        Route route1 = new Route(ObjectId.get(), "Dolnyslask");
        Order o1 = new Order(null, "Order 1", components1, LocalDate.now(), route1, components1.size(), 2, Stage.BASE, 1L);

        // Order 2
        Set<Component> components2 = new HashSet<>();
        components2.add(new Component(4L, 1, 2, Lists.newArrayList(), LocalDate.now(), true));
        components2.add(new Component(5L, 1, 2, Lists.newArrayList(), LocalDate.now(), true));
        components2.add(new Component(6L, 1, 2, Lists.newArrayList(), LocalDate.now(), true));
        Route route2 = new Route(ObjectId.get(), "Dolnyslask");
        Order o2 = new Order(null, "Order 1", components2, LocalDate.now(), route2, components2.size(), 0, Stage.GRINDING, 2L);
        orderDao.save(o1);
        orderDao.save(o2);


    }
}
