package com.frontwit.barcodeapp.rest;

import com.frontwit.barcodeapp.dto.OrderDetailDto;
import com.frontwit.barcodeapp.dto.OrderDto;
import com.frontwit.barcodeapp.dto.OrderSearchCriteria;
import com.frontwit.barcodeapp.logic.CsvService;
import com.frontwit.barcodeapp.logic.OrderService;
import com.frontwit.barcodeapp.logic.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    private OrderService orderService;

    private PdfService pdfService;

    private CsvService csvService;

    @Autowired
    public void setOrderService(OrderService orderService, PdfService pdfService, CsvService csvService) {
        this.orderService = orderService;
        this.pdfService = pdfService;
        this.csvService = csvService;
    }

    @GetMapping
    Page<OrderDto> getOrders(Pageable pageable) {
        return orderService.getOrders(pageable);
    }

    @PostMapping
    public ResponseEntity saveOrder(@RequestParam(value = "file") MultipartFile file) {
        OrderDetailDto orderToSave = csvService.parse(file);
//        orderService.save(orderToSave);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
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
        byte[] pdfDoc = pdfService.generatePdf(dto.name, dto.components);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfDoc);

    }
}
