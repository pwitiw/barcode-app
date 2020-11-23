package com.frontwit.barcodeapp.administration.catalogue.barcodes;

import com.frontwit.barcodeapp.administration.catalogue.dto.FrontDto;
import com.frontwit.barcodeapp.administration.catalogue.dto.OrderDetailDto;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static com.itextpdf.text.Element.ALIGN_CENTER;
import static java.awt.Font.PLAIN;
import static java.lang.String.format;

@AllArgsConstructor
@Service
public class BarcodePdfGenerator {
    private static final float MARGIN_BASE = 28.3f;
    private static final int COLUMN_AMOUNT = 5;

    public BarcodePdf createBarcodesFor(OrderDetailDto orderDetails) {
        Document document = new Document(PageSize.A4, MARGIN_BASE, MARGIN_BASE, MARGIN_BASE, MARGIN_BASE);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            var pdfWriter = PdfWriter.getInstance(document, out);
            document.open();
            var table = new PdfPTable(COLUMN_AMOUNT);
            table.setWidthPercentage(100f);
            for (var front : orderDetails.getFronts()) {
                for (var i = 0; i < front.getQuantity(); i++) {

                    table.addCell(createFontCell(i + 1, orderDetails, front, pdfWriter.getDirectContent()));
                }
            }
            fillTableWithEmptyCells(orderDetails, table);
            document.add(table);
            document.close();
        } catch (DocumentException e) {
            throw new BarcodeGenerationException("Error while generating barcodes", e);
        }
        return new BarcodePdf(out);
    }

    private void fillTableWithEmptyCells(OrderDetailDto orderDetails, PdfPTable table) {
        for (var i = totalElements(orderDetails) % COLUMN_AMOUNT; i % COLUMN_AMOUNT > 0; i++) {
            table.addCell(emptyCell());
        }
    }

    private Integer totalElements(OrderDetailDto orderDetails) {
        return orderDetails.getFronts().stream()
                .map(FrontDto::getQuantity)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private PdfPCell createFontCell(int nr, OrderDetailDto order, FrontDto front, PdfContentByte cb) {
        var cell = emptyCell();
        cell.setPadding(5f);
        cell.addElement(centeredParagraph(format("%s", order.getRoute())));
        cell.addElement(centeredParagraph(format("%s", order.getCustomer())));
        cell.addElement(nameWithQuantity(order.getName(), totalElements(order)));
        cell.addElement(dimensionsWithQuantity(nr, front));
        cell.addElement(centeredParagraph(String.valueOf(front.getBarcode()), 7));
        return cell;
    }

    private Paragraph nameWithQuantity(String orderName, int quantity) {
        return centeredParagraph(format("%s - %s szt.", orderName, quantity));
    }

    private PdfPCell emptyCell() {
        var cell = new PdfPCell();
        cell.setPadding(5f);
        cell.setFixedHeight((float) Math.floor((PageSize.A4.getHeight() - 2 * MARGIN_BASE) / 13));
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private Paragraph centeredParagraph(String content) {
        return centeredParagraph(content, 8);
    }

    private Paragraph centeredParagraph(String content, int size) {
        var paragraph = new Paragraph(content, new Font(Font.FontFamily.UNDEFINED, size, PLAIN));
        paragraph.setMultipliedLeading(1.1f);
        paragraph.setAlignment(ALIGN_CENTER);
        return paragraph;
    }

    private Paragraph dimensionsWithQuantity(int nr, FrontDto front) {
        String result = format("%s x %s (%s/%s)", front.getHeight(), front.getWidth(), nr, front.getQuantity());
        return centeredParagraph(result);
    }

//    private Image createBarcode(FrontDto front, PdfContentByte cb) {
//        var barcode = new Barcode39();
//        barcode.setCode(String.valueOf(front.getBarcode()));
//        barcode.setStartStopText(false);
//        barcode.setBarHeight(15);
//        barcode.setFont(null);
//        return barcode.createImageWithBarcode(cb, null, null);
//    }
}
