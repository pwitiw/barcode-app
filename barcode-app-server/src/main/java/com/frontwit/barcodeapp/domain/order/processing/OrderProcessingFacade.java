package com.frontwit.barcodeapp.domain.order.processing;

import com.frontwit.barcodeapp.domain.order.OrderDetailDto;
import com.frontwit.barcodeapp.domain.order.processing.dto.OrderNotFoundException;
import com.frontwit.barcodeapp.domain.order.processing.dto.ProcessCommand;

import java.util.List;

public class OrderProcessingFacade {

    public void update(List<ProcessCommand> commands) {
//        commands.forEach(c ->);
    }

    public OrderDetailDto getOrder(long barcode) {
        throw new OrderNotFoundException(barcode);
    }
}
