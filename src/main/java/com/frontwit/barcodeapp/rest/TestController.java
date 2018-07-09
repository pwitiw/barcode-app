package com.frontwit.barcodeapp.rest;

import com.frontwit.barcodeapp.dao.repository.OrderRepository;
import com.frontwit.barcodeapp.logic.BarCodeGenerator;
import com.frontwit.barcodeapp.logic.PdfGenerator;
import com.frontwit.barcodeapp.model.Order;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    private OrderRepository orderRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping(path = "order/{id}/barcode")
    public ResponseEntity<byte[]> getBarcodeForOrder(@PathVariable("id") long id) throws DocumentException {

        Order o = orderRepository.findOne(id);
        if (o != null) {
            List<byte[]> barcodeAsBytes = BarCodeGenerator.getBarCodesAsByteArrays(o);
            byte[] pdfAsBytes = PdfGenerator.createPdfAsBytes(o.getName(), barcodeAsBytes);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfAsBytes);
        }
        return ResponseEntity
                .badRequest()
                .build();
    }
}
