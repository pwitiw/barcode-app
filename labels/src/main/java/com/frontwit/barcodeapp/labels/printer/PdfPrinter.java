package com.frontwit.barcodeapp.labels.printer;

import com.frontwit.barcodeapp.labels.application.QrCodePreparationException;
import com.frontwit.barcodeapp.labels.application.LabelPrinter;
import com.frontwit.barcodeapp.labels.application.LabelResult;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.viewerpreferences.PDViewerPreferences;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.printing.PDFPrintable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.PageRanges;
import javax.print.attribute.standard.PrinterName;
import javax.print.attribute.standard.Sides;
import java.awt.print.*;
import java.util.Optional;

import static java.lang.String.format;

@Service
@SuppressWarnings({"ClassDataAbstractionCoupling", "PMD.UnusedPrivateMethod", "ClassFanOutComplexity"})
public class PdfPrinter implements LabelPrinter {
    private static final Logger LOGGER = LoggerFactory.getLogger(PdfPrinter.class);

    private static final String QR_CODE_PRINTING_ERROR = "Error while qr code printing";
    private final String printerName;

    public PdfPrinter(@Value("${barcode-app.labels.printer.name}") String printerName) {
        this.printerName = printerName;
    }

    @Override
    public void print(LabelResult labelResult) {
        try (PDDocument document = PDDocument.load(labelResult.asStream().toByteArray())) {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(document));
            job.setJobName("Front: " + labelResult.getBarcode());
            var printService = getPrintService();
            if (printService.isEmpty()) {
                LOGGER.warn(format("Printer %s not found", printerName));
                return;
            }
            job.setPrintService(printService.get());
            job.print();
        } catch (Exception ex) {
            throw new QrCodePreparationException(QR_CODE_PRINTING_ERROR, ex);
        }
    }

    public void printWithAttributes(LabelResult labelResult) {
        try (PDDocument document = PDDocument.load(labelResult.asStream().toByteArray())) {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(document));

            PrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
            attr.add(new PageRanges(1, 1)); // pages 1 to 1

            job.print(attr);
        } catch (
                Exception ex) {
            throw new QrCodePreparationException(QR_CODE_PRINTING_ERROR, ex);
        }

    }

    public void printWithDialog(LabelResult labelResult) {
        try (PDDocument document = PDDocument.load(labelResult.asStream().toByteArray())) {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(document));
            if (job.printDialog()) {
                job.print();
            }
        } catch (Exception ex) {
            throw new QrCodePreparationException(QR_CODE_PRINTING_ERROR, ex);
        }
    }

    public void printWithDialogAndAttributes(PDDocument document) throws PrinterException {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));
        PrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
        attr.add(new PageRanges(1, 1)); // pages 1 to 1
        // attr.add(new MediaPrintableArea(0f, 0f, 60f, 60f, MediaPrintableArea.MM));

        PDViewerPreferences vp = document.getDocumentCatalog().getViewerPreferences();
        if (vp != null && vp.getDuplex() != null) {
            String dp = vp.getDuplex();
            if (PDViewerPreferences.DUPLEX.DuplexFlipLongEdge.toString().equals(dp)) {
                attr.add(Sides.TWO_SIDED_LONG_EDGE);
            } else if (PDViewerPreferences.DUPLEX.DuplexFlipShortEdge.toString().equals(dp)) {
                attr.add(Sides.TWO_SIDED_SHORT_EDGE);
            } else if (PDViewerPreferences.DUPLEX.Simplex.toString().equals(dp)) {
                attr.add(Sides.ONE_SIDED);
            }
        }

        if (job.printDialog(attr)) {
            job.print(attr);
        }
    }

    private static void printWithPaper(PDDocument document) throws PrinterException {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));

        // define custom paper
        Paper paper = new Paper();
        paper.setSize(306, 396); // 1/72 inch
        paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight()); // no margins

        // custom page format
        PageFormat pageFormat = new PageFormat();
        pageFormat.setPaper(paper);

        // override the page format
        Book book = new Book();
        // append all pages
        book.append(new PDFPrintable(document), pageFormat, document.getNumberOfPages());
        job.setPageable(book);

        job.print();
    }

    private Optional<PrintService> getPrintService() {
        AttributeSet aset = new HashAttributeSet();
        aset.add(new PrinterName(printerName, null));
        PrintService[] pservices = PrintServiceLookup.lookupPrintServices(null, aset);
        if (pservices.length == 0) {
            return Optional.empty();
        }
        return Optional.of(pservices[0]);
    }
}
