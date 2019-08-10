package com.frontwit.barcodeapp.administration.application.order;

import com.frontwit.barcodeapp.administration.application.order.dto.ProcessCommand;
import com.frontwit.barcodeapp.administration.application.ports.OrderDao;
import com.frontwit.barcodeapp.administration.application.synchronization.SynchronizationFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
class OrderProcessingService {
    private static final Logger LOG = LoggerFactory.getLogger(OrderProcessingService.class);

    private OrderDao orderDao;
    private SynchronizationFacade orderSynchronizer;

    public OrderProcessingService(OrderDao orderDao, SynchronizationFacade orderSynchronizer) {
        this.orderDao = orderDao;
        this.orderSynchronizer = orderSynchronizer;
    }

    public void update(List<ProcessCommand> commands) {
        commands
                .forEach(command -> {
                    long id = BarcodeConverter.toId(command.getBarcode());
                    Order order = orderDao
                            .findOne(id)
                            .orElse(synchronize(id));
                    if (order != null) {
                        order.update(command);
                        orderDao.save(order);
                        LOG.info(format("Updated order: %s", order));

                    }
                });
    }

    private Order synchronize(Long id) {
        return orderSynchronizer
                .getOrder(id)
                .map(Order::valueOf)
                .orElseGet(() -> {
                    LOG.info("No order synchronized");
                    return null;
                });
    }
}
