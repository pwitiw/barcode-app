package com.frontwit.barcodeapp;

import com.frontwit.barcodeapp.dao.repository.OrderRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BarcodeappApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext a = SpringApplication.run(BarcodeappApplication.class, args);
        OrderRepository orderRepository = a.getBean(OrderRepository.class);
//        CounterService counterService = a.getBean(CounterService.class);
//        Long id = counterService.getNextOrderId();
//        Order order = new Order();
//        order.setId(id);
//        order.setName("Name");
//        Component component = new Component();
//        component.setBarcode(new Barcode(BigInteger.valueOf(id)));
//        component.setHeight(1);
//        component.setWidth(1);
//        Process process = new Process();
//        process.setStage(Stage.GRINDING);
//        order.setComponents(Sets.newHashSet(component));
//        component.setProcessingHistory(Lists.newArrayList(process));
//        orderRepository.save(order);
    }
}
