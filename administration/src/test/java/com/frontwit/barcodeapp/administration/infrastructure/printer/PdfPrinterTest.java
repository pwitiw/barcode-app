package com.frontwit.barcodeapp.administration.infrastructure.printer;

import com.frontwit.barcodeapp.administration.catalogue.orders.barcodes.BarcodePdf;
import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;

import java.awt.print.PrinterException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class PdfPrinterTest {

    public static void main(String[] args) throws PrinterException, IOException {
        PdfPrinter pdfPrinter = new PdfPrinter("zebra-codes");
        byte[] bytes = FileUtils.readFileToByteArray(new File("qr.pdf"));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(bytes);
        pdfPrinter.print(new BarcodePdf(new Barcode(1234L), out));
    }
}
