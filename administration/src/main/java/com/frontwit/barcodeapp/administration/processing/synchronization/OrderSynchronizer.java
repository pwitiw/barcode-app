package com.frontwit.barcodeapp.administration.processing.synchronization;

import com.frontwit.barcodeapp.administration.processing.front.model.FrontNotFound;
import com.frontwit.barcodeapp.administration.processing.shared.OrderId;
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvents;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;

import static java.lang.String.format;

@AllArgsConstructor
public class OrderSynchronizer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderSynchronizer.class);

    private SourceOrderRepository sourceOrderRepository;
    private SaveSynchronizedFronts saveSynchronizedFronts;
    private SaveSynchronizedOrder saveSynchronizedOrder;
    private CheckSynchronizedOrder checkSynchronizedOrder;
    private OrderMapper orderMapper;
    private DomainEvents domainEvents;
    private SynchronizationRepository synchronizationRepository;

    @EventListener
    public void synchronize(FrontNotFound event) {
        if (!checkSynchronizedOrder.isSynchronized(event.getOrderId())) {
            sourceOrderRepository.findBy(event.getOrderId()).ifPresentOrElse(
                    order -> {
                        var dictionary = sourceOrderRepository.getDictionary();
                        saveOrderWithFronts(order, dictionary);
                        domainEvents.publish(new FrontSynchronized(event.getDelayedProcessFrontCommand()));
                    },
                    () -> LOGGER.warn("Order not found {}", event.getOrderId()));
        }
    }

    public long synchronizeOrders() {
        var lastSyncDate = synchronizationRepository.getLastSynchronizationDate();
        var dictionary = sourceOrderRepository.getDictionary();
        synchronizationRepository.updateSyncDate();
        LOGGER.debug(format("Synchronization for date: %s", lastSyncDate));
        long count = sourceOrderRepository.findByDateBetween(lastSyncDate)
                .stream()
                .filter(sourceOrder -> !checkSynchronizedOrder.isSynchronized(new OrderId(sourceOrder.getId())))
                .peek(sourceOrder -> saveOrderWithFronts(sourceOrder, dictionary))
                .count();
        LOGGER.debug(format("Synchronization for date: %s  ====> %s orders", lastSyncDate, count));
        return count;
    }

    private void saveOrderWithFronts(SourceOrder sourceOrder, Dictionary dictionary) {
        var targetOrder = orderMapper.map(sourceOrder, dictionary);
        saveSynchronizedOrder.save(targetOrder);
        saveSynchronizedFronts.save(targetOrder.getFronts());
        LOGGER.info(format("OrderSynchronized {id=%s, frontsNr=%s}", targetOrder.getOrderId().getId(), targetOrder.getFronts().size()));
    }
}
