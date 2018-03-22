package com.frontwit.barcodeapp.logic;

import com.frontwit.barcodeapp.entity.Order;
import com.frontwit.barcodeapp.repository.OrderRepository;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TestController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping(path = "order/{id}/barcode")
    public ResponseEntity<byte[]> getBarcodeForOrder(@PathVariable("id") long id) throws DocumentException {

        Order o = orderRepository.findOne(id);
        if (o != null) {
            Collection<Number> barcodes = o.getComponents()
                    .stream()
                    .map(c -> c.getBarcode().getValue())
                    .collect(Collectors.toSet());
            List<byte[]> barcodeAsBytes = BarCodeGenerator.getBarCodesAsByteArrays(barcodes);
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
