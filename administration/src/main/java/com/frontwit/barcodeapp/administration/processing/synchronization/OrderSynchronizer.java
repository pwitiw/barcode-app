package com.frontwit.barcodeapp.administration.processing.synchronization;

import com.frontwit.barcodeapp.administration.processing.front.model.FrontNotFound;
import com.frontwit.barcodeapp.administration.processing.front.model.FrontRepository;
import com.frontwit.barcodeapp.administration.processing.order.model.OrderRepository;
import com.frontwit.barcodeapp.administration.processing.shared.OrderId;
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvents;
import com.frontwit.barcodeapp.administration.statistics.domain.OrderPlaced;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;

import java.time.Instant;

@AllArgsConstructor
public class OrderSynchronizer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderSynchronizer.class);

    private SourceRepository sourceRepository;
    private FrontRepository frontRepository;
    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private DomainEvents domainEvents;
    private SynchronizationRepository synchronizationRepository;

    @EventListener
    public void synchronize(FrontNotFound event) {
        if (orderRepository.isNotSynchronized(event.getOrderId())) {
            sourceRepository.findBy(event.getOrderId())
                    .ifPresentOrElse(
                            order -> {
                                var savedOrder = saveOrderWithFronts(order, sourceRepository.getDictionary());
                                domainEvents.publish(
                                        frontSynchronized(event),
                                        orderPlaced(savedOrder)
                                );
                            },
                            () -> LOGGER.warn("Order not found {}", event.getOrderId()));
        }
    }

    public long synchronize() {
        Instant now = Instant.now();
        var lastSyncDate = synchronizationRepository.getLastSynchronizationDate();
        LOGGER.info("Synchronization for period: {} - {}", lastSyncDate, now);
        var dictionary = sourceRepository.getDictionary();
        long count = sourceRepository.findByDateBetween(lastSyncDate, now)
                .stream()
                .filter(sourceOrder -> orderRepository.isNotSynchronized(new OrderId(sourceOrder.getId())))
                .peek(sourceOrder -> saveOrderWithFronts(sourceOrder, dictionary))
                .count();
        synchronizationRepository.updateSyncDate(now);
        LOGGER.info("Synchronization finished. Synchronized {} orders.", count);
        return count;
    }

    public void synchronizeStatistics() {
        Instant beginningOf2020 = Instant.ofEpochMilli(1_577_836_800_000L);
        var lastSyncDate = synchronizationRepository.getLastSynchronizationDate();
        var dictionary = sourceRepository.getDictionary();
        sourceRepository.findByDateBetween(beginningOf2020, lastSyncDate)
                .stream()
                .filter(sourceOrder -> !orderRepository.isNotSynchronized(new OrderId(sourceOrder.getId())))
                .forEach(sourceOrder -> {
                    var targetOrder = orderMapper.map(sourceOrder, dictionary);
                    domainEvents.publish(orderPlaced(targetOrder));
                });
        LOGGER.info("Synchronized statistics for period {} - {} ", beginningOf2020, lastSyncDate);
    }

    private static FrontSynchronized frontSynchronized(FrontNotFound event) {
        return new FrontSynchronized(event.getBarcode(), event.getStage(), event.getDateTime());
    }

    private TargetOrder saveOrderWithFronts(SourceOrder sourceOrder, Dictionary dictionary) {
        var targetOrder = orderMapper.map(sourceOrder, dictionary);
        orderRepository.save(targetOrder);
        frontRepository.save(targetOrder.getFronts());
        domainEvents.publish(orderPlaced(targetOrder));
        LOGGER.info("OrderSynchronized {id={}, name={}, customer={},orderedAt={}, frontsNr={}}",
                targetOrder.getOrderId().getId(),
                targetOrder.getInfo().getName(),
                targetOrder.getCustomerId(),
                targetOrder.getInfo().getOrderedAt(),
                targetOrder.getFronts().size()
        );
        return targetOrder;
    }

    private static OrderPlaced orderPlaced(TargetOrder order) {
        var meters = MetersCalculator.calculate(order.getFronts());
        return new OrderPlaced(meters, order.getInfo().getOrderedAt(), order.getInfo().getType());
    }
}
