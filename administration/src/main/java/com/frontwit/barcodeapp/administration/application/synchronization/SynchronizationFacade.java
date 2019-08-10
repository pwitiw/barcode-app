package com.frontwit.barcodeapp.administration.application.synchronization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontwit.barcodeapp.administration.application.order.dto.OrderDetailDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class SynchronizationFacade {
    private static final Logger LOG = LoggerFactory.getLogger(SynchronizationFacade.class);

    private ObjectMapper jsonMapper;
    private SynchronizationRepository synchronizationRepository;

    public Optional<OrderDetailDto> getOrder(long id) {
        OrderDetailDto result = null;
        try {
            Order order = synchronizationRepository
                    .findOrder(id)
                    .orElseThrow(() -> new SyncOrderNotFoundException(id));

            Dictionary dictionary = synchronizationRepository.findDictionary();

            result = order.composeOrderDetailDto(jsonMapper, dictionary);
            LOG.debug("Order from Frontwit DB: " + order);
        } catch (Exception ex) {
            LOG.debug(format("Order %s does not exist in Frontwit DB.", id));
        }
        return Optional.ofNullable(result);
    }
}
