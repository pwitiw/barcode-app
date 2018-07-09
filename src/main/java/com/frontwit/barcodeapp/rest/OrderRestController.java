package com.frontwit.barcodeapp.rest;

import com.frontwit.barcodeapp.dto.OrderDetailDto;
import com.frontwit.barcodeapp.dto.OrderDto;
import com.frontwit.barcodeapp.dto.OrderSearchCriteria;
import com.frontwit.barcodeapp.logic.BarcodeService;
import com.frontwit.barcodeapp.logic.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    //TODO przypadki uzycia -resty rozpisac
    //TODO anty cors
    //TODO security

    private OrderService orderService;

    private BarcodeService barcodeService;

    @Autowired
    public void setOrderService(OrderService orderService, BarcodeService barcodeService) {
        this.orderService = orderService;
        this.barcodeService = barcodeService;
    }

    @GetMapping
    Page<OrderDto> getOrders(Pageable pageable) {
        return orderService.getOrders(pageable);
    }

    @GetMapping("{id}")
    OrderDetailDto getOrder(@PathVariable String id) {
        return orderService.getOrder(id);
    }

    @PostMapping("/search")
    Page<OrderDto> getOrdersForSearchCriteria(Pageable pageable, @RequestBody OrderSearchCriteria searchCriteria) {
        return orderService.getOrders(pageable, searchCriteria);
    }

    @GetMapping(path = "/{id}/barcode")
    public ResponseEntity<byte[]> getBarcodesForOrder(@PathVariable String id) {
        OrderDetailDto dto = orderService.getOrder(id);
        byte[] pdfDoc = barcodeService.generatePdf(dto.name, dto.components);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfDoc);

    }
}
