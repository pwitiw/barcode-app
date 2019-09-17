package com.frontwit.barcodeapp.administration.order.processing.synchronization;

import com.frontwit.barcodeapp.administration.order.processing.shared.OrderId;
import lombok.AllArgsConstructor;

import static java.lang.String.format;

@AllArgsConstructor
public class OrderSynchronizer {

    private SourceOrderRepository sourceOrderRepository;
    private SaveSynchronizedFronts saveSynchronizedFronts;
    private SaveSynchronizedOrder saveSynchronizedOrder;
    private OrderMapper orderMapper;

    public void synchronize(OrderId id) {
        var sourceOrder = sourceOrderRepository.findBy(id)
                .orElseThrow(() -> new IllegalStateException(format("No order for id %s", id.getOrderId())));
        var dictionary = sourceOrderRepository.getDictionary();
        var targetOrder = orderMapper.map(sourceOrder, dictionary);
        saveSynchronizedOrder.save(targetOrder);
        saveSynchronizedFronts.save(targetOrder.getFronts());
    }
}
