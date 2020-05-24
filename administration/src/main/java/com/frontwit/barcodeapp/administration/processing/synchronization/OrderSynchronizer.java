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

    private SourceRepository sourceRepository;
    private SaveSynchronizedFronts saveSynchronizedFronts;
    private SaveSynchronizedOrder saveSynchronizedOrder;
    private SaveSynchronizedCustomer saveSynchronizedCustomers;
    private CheckSynchronizedOrder checkSynchronizedOrder;
    private OrderMapper orderMapper;
    private DomainEvents domainEvents;
    private SynchronizationRepository synchronizationRepository;

    @EventListener
    public void synchronize(FrontNotFound event) {
        if (!checkSynchronizedOrder.isSynchronized(event.getOrderId())) {
            sourceRepository.findBy(event.getOrderId()).ifPresentOrElse(
                    order -> {
                        var dictionary = sourceRepository.getDictionary();
                        saveOrderWithFronts(order, dictionary);
                        domainEvents.publish(new FrontSynchronized(event.getDelayedProcessFrontCommand()));
                    },
                    () -> LOGGER.warn("Order not found {}", event.getOrderId()));
        }
    }

    public long synchronizeOrders() {
        var lastSyncDate = synchronizationRepository.getLastSynchronizationDate();
        var dictionary = sourceRepository.getDictionary();
        long count = sourceRepository.findByDateBetween(lastSyncDate)
                .stream()
                .filter(sourceOrder -> !checkSynchronizedOrder.isSynchronized(new OrderId(sourceOrder.getId())))
                .peek(sourceOrder -> saveOrderWithFronts(sourceOrder, dictionary))
                .count();
        synchronizationRepository.updateSyncDate();
        LOGGER.debug(format("Synchronized {date= %s, orders=%s}", lastSyncDate, count));
        return count;
    }

    private void saveOrderWithFronts(SourceOrder sourceOrder, Dictionary dictionary) {
        var targetOrder = orderMapper.map(sourceOrder, dictionary);
        saveSynchronizedOrder.save(targetOrder);
        saveSynchronizedFronts.save(targetOrder.getFronts());
        saveSynchronizedCustomers.save(targetOrder.getCustomer());
        LOGGER.info("OrderSynchronized {id={}, customer={}, frontsNr={}}", targetOrder.getOrderId().getId(), targetOrder.getCustomer().getName(), targetOrder.getFronts().size());
    }
}
