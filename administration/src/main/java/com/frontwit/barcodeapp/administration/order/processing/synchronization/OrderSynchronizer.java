package com.frontwit.barcodeapp.administration.order.processing.synchronization;

import com.frontwit.barcodeapp.administration.order.processing.front.model.FrontNotFound;
import com.frontwit.barcodeapp.administration.order.processing.shared.ProcessingException;
import com.frontwit.barcodeapp.administration.order.processing.shared.events.DomainEvents;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;

import static java.lang.String.format;

@AllArgsConstructor
public class OrderSynchronizer {

    private SourceOrderRepository sourceOrderRepository;
    private SaveSynchronizedFronts saveSynchronizedFronts;
    private SaveSynchronizedOrder saveSynchronizedOrder;
    private CheckSynchronizedOrder checkSynchronizedOrder;
    private OrderMapper orderMapper;
    private DomainEvents domainEvents;


    @EventListener
    public void synchronize(FrontNotFound event) {
        if (!checkSynchronizedOrder.isSynchronized(event.getOrderId())) {
            var sourceOrder = sourceOrderRepository.findBy(event.getOrderId())
                    .orElseThrow(() -> new ProcessingException(format("No order for id %s", event.getOrderId().getOrderId())));
            var dictionary = sourceOrderRepository.getDictionary();
            var targetOrder = orderMapper.map(sourceOrder, dictionary);
            saveSynchronizedOrder.save(targetOrder);
            saveSynchronizedFronts.save(targetOrder.getFronts());
            domainEvents.publish(new FrontSynchronized(event.getDelayedProcessFrontCommand()));
        }
    }

}
