package com.frontwit.barcodeapp.administration.processing.synchronization;

import com.frontwit.barcodeapp.administration.processing.front.model.FrontNotFound;
import com.frontwit.barcodeapp.administration.processing.shared.OrderId;
import com.frontwit.barcodeapp.administration.processing.shared.ProcessingException;
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvents;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;

import java.time.LocalDate;

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
            saveOrderWithFronts(sourceOrder, dictionary);
            domainEvents.publish(new FrontSynchronized(event.getDelayedProcessFrontCommand()));
        }
    }

    public long synchronizeOrders() {
        // TODO read /write to Database
        var lastSyncDate = LocalDate.now().minusDays(3);
        var today = LocalDate.now();
        var dictionary = sourceOrderRepository.getDictionary();
        return sourceOrderRepository.findByDateBetween(lastSyncDate, today)
                .stream()
                .filter(sourceOrder -> !checkSynchronizedOrder.isSynchronized(new OrderId(sourceOrder.getId())))
                .peek(sourceOrder -> saveOrderWithFronts(sourceOrder, dictionary))
                .count();
    }

    private void saveOrderWithFronts(SourceOrder sourceOrder, Dictionary dictionary) {
        var targetOrder = orderMapper.map(sourceOrder, dictionary);
        saveSynchronizedOrder.save(targetOrder);
        saveSynchronizedFronts.save(targetOrder.getFronts());
    }
}
