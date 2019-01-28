package com.frontwit.barcodeapp.rest;

import com.frontwit.barcodeapp.dto.OrderDetailDto;
import com.frontwit.barcodeapp.dto.OrderDto;
import com.frontwit.barcodeapp.dto.OrderSearchCriteria;
import com.frontwit.barcodeapp.dto.ProcessDto;
import com.frontwit.barcodeapp.logic.OrderService;
import com.frontwit.barcodeapp.logic.PdfService;
import com.frontwit.barcodeapp.synchronization.SynchronizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/orders")
public class OrderResource {

    private OrderService orderService;

    private PdfService pdfService;

    private SynchronizationService synchronizationService;

    @Autowired
    public void setOrderService(OrderService orderService, PdfService pdfService, SynchronizationService synchronizationService) {
        this.orderService = orderService;
        this.pdfService = pdfService;
        this.synchronizationService = synchronizationService;
    }

    @GetMapping
    Page<OrderDto> getOrders(Pageable pageable) {
        return orderService.getOrders(pageable);
    }

    @PostMapping("/synchronize")
    public ResponseEntity synchronize() {
        List<OrderDetailDto> ordersToSave = synchronizationService.getOrders();
        orderService.save(ordersToSave);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{barcode}")
    OrderDetailDto getOrder(@PathVariable Long barcode) {
        return orderService.getOrder(barcode);
    }

    @PostMapping("/search")
    Page<OrderDto> getOrdersForSearchCriteria(Pageable pageable, @RequestBody OrderSearchCriteria searchCriteria) {
        return orderService.getOrders(pageable, searchCriteria);
    }

    @GetMapping("/{barcode}/barcode")
    public ResponseEntity<byte[]> getBarcodesForOrder(@PathVariable Long barcode) {
        OrderDetailDto dto = orderService.getOrder(barcode);
        byte[] pdfDoc = pdfService.generatePdf(dto.getBarcode(), dto.getComponents());
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfDoc);
    }

    @PostMapping("/barcode")
    public void saveBarcodes(@RequestBody List<ProcessDto> barcodes) {
        orderService.updateOrdersForProcesses(barcodes);
    }
}
