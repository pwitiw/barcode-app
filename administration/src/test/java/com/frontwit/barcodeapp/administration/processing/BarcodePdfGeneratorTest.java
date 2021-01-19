package com.frontwit.barcodeapp.administration.processing;

import com.frontwit.barcodeapp.administration.catalogue.barcodes.BarcodePdf;
import com.frontwit.barcodeapp.administration.catalogue.barcodes.BarcodesX21PdfGenerator;
import com.frontwit.barcodeapp.administration.catalogue.barcodes.BarcodesX64PdfGenerator;
import com.frontwit.barcodeapp.administration.catalogue.dto.FrontDto;
import com.frontwit.barcodeapp.administration.catalogue.dto.OrderDetailDto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class BarcodePdfGeneratorTest {

    public static void main(String args[]) throws IOException {
        var pdfGenerator = new BarcodesX21PdfGenerator();
        var file = new File("test.pdf");
        var outputStream = new FileOutputStream(file);

        BarcodePdf barcodesFor = pdfGenerator.createBarcodesFor(
                Arrays.asList(
                        new OrderDetailDto(
                                1L, "TW202", "", "",
                                "", "", "Kasperczyk", "Ostrów Wielkopolski", null, null,
                                Arrays.asList(
                                        new FrontDto(11101L, 1230, 1300, 10, null, "", null, null),
                                        new FrontDto(111101L, 1230, 1300, 10, null, "", null, null),
                                        new FrontDto(10000000L, 1230, 1300, 10, null, "", null, null),
                                        new FrontDto(10004555123L, 1230, 1300, 10, null, "", null, null),
                                        new FrontDto(10003415512223L, 1230, 1300, 35, null, "", null, null)
                                ),
                                true, true, 1L, null, null),
                        new OrderDetailDto(
                                1L, "TW202", "", "",
                                "", "", "Kasperczyk", "Ostrów Wielkopolski", null, null,
                                Arrays.asList(
                                        new FrontDto(21101L, 2230, 2300, 10, null, "", null, null),
                                        new FrontDto(211101L, 2230, 2300, 10, null, "", null, null),
                                        new FrontDto(20000000L, 2230, 2300, 10, null, "", null, null),
                                        new FrontDto(20004555123L, 2230, 2300, 10, null, "", null, null),
                                        new FrontDto(20003415512223L, 2230, 2300, 35, null, "", null, null)
                                ),
                                true, true, 1L, null, null)
                )
        );
        barcodesFor.asStream().writeTo(outputStream);
    }
}
