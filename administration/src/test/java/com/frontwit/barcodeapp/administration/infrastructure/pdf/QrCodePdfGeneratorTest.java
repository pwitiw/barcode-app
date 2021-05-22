package com.frontwit.barcodeapp.administration.infrastructure.pdf;

import com.frontwit.barcodeapp.administration.domain.qr.QrCodeInfo;
import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.processing.shared.Dimensions;
import com.frontwit.barcodeapp.administration.processing.shared.OrderId;

import java.io.FileOutputStream;
import java.io.IOException;

public class QrCodePdfGeneratorTest {
    public static void main(String[] args) throws IOException {
        var barcode = Barcode.valueOf(new OrderId(1000), 2);
        var info = new QrCodeInfo(
                new QrCodeInfo.QrOrder(
                        barcode.getOrderId().getId(),
                        "TW202",
                        3,
                        "Wrocław",
                        "Kowalski"
                ),
                new QrCodeInfo.QrFront(
                        barcode.getBarcode(),
                        new Dimensions(800, 10400),
                        1
                )
        );
        var qrCode = new QrCodePdfGenerator().create(info);
        var outputStream = new FileOutputStream("qr.pdf");
        qrCode.asStream().writeTo(outputStream);
    }
}
