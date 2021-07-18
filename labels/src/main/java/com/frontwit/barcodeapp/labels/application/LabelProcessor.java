package com.frontwit.barcodeapp.labels.application;

import com.frontwit.barcodeapp.labels.pdf.LabelPdfGenerator;
import com.frontwit.barcodeapp.labels.printer.PdfPrinter;
import org.springframework.stereotype.Component;

@Component
public class LabelProcessor {
    private final LabelGenerator labelGenerator;
    private final PdfPrinter printer;

    public LabelProcessor(LabelPdfGenerator labelGenerator, PdfPrinter printer) {
        this.labelGenerator = labelGenerator;
        this.printer = printer;
    }

   public void process(LabelInfo labelInfo) {
        var label = labelGenerator.create(labelInfo);
        printer.print(label);
    }
}
