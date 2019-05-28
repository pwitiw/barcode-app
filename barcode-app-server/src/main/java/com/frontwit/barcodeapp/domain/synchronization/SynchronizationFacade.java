package com.frontwit.barcodeapp.domain.synchronization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontwit.barcodeapp.domain.order.dto.OrderDetailDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SynchronizationFacade {
    private static final Logger LOG = LoggerFactory.getLogger(SynchronizationFacade.class);

    private ObjectMapper jsonMapper;
    private SynchronizationRepository synchronizationRepository;

    public OrderDetailDto getOrder(long id) {
        Order order = synchronizationRepository
                .findOrder(id)
                .orElseThrow(() -> new SyncOrderNotFoundException(id));
        LOG.debug("Order from Frontwit DB: " + order);
        Dictionary dictionary = synchronizationRepository.findDictionary();
        return order.composeOrderDetailDto(jsonMapper, dictionary);
    }
}
