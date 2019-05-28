package com.frontwit.barcodeapp.domain.order;

import com.frontwit.barcodeapp.domain.order.dto.OrderDetailDto;
import com.frontwit.barcodeapp.domain.order.dto.ProcessCommand;
import com.frontwit.barcodeapp.domain.order.ports.OrderRepository;
import com.frontwit.barcodeapp.domain.synchronization.SynchronizationFacade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class OrderProcessingService {

    private OrderRepository orderRepository;
    private SynchronizationFacade orderSynchronizer;

    // TODO zrobic mongo beana
    public OrderProcessingService(OrderRepository orderRepository, SynchronizationFacade orderSynchronizer) {
        this.orderRepository = orderRepository;
        this.orderSynchronizer = orderSynchronizer;
    }

    public void update(List<ProcessCommand> commands) {
        commands
                .forEach(command -> {
                    long id = BarcodeConverter.toId(command.getBarcode());
                    Order order = orderRepository
                            .findOne(id)
                            .orElse(synchronize(id));
                    order.update(command);
                    orderRepository.save(order);
                });
    }

    private Order synchronize(Long id) {
        OrderDetailDto dto = orderSynchronizer.getOrder(id);
//        return Order.toEntity(dto);
        return null;
    }
}
