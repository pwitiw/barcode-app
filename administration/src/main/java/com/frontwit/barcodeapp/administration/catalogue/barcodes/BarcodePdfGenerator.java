package com.frontwit.barcodeapp.administration.catalogue.barcodes;

import com.frontwit.barcodeapp.administration.catalogue.dto.FrontDto;
import com.frontwit.barcodeapp.administration.catalogue.dto.OrderDetailDto;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Collections;

import static com.itextpdf.text.Element.ALIGN_CENTER;
import static java.awt.Font.BOLD;
import static java.lang.String.format;

@AllArgsConstructor
@Service
public class BarcodePdfGenerator {
    public static void main(String[] args) throws IOException {
        var pdfGenerator = new BarcodePdfGenerator();
        var file = new File("test.pdf");
        var outputStream = new FileOutputStream(file);

        BarcodePdf barcodesFor = pdfGenerator.createBarcodesFor(
                new OrderDetailDto(
                        1L, "TW202", "", "",
                        "", "", "", "", null, null,
                        Collections.singletonList(
                                new FrontDto(100034123L, 1230, 1300, 409, null, "", null, null)
                        ),
                        true, true, 1L, null, null)
        );
        barcodesFor.asStream().writeTo(outputStream);
    }

    public BarcodePdf createBarcodesFor(OrderDetailDto orderDetails) {
        Document document = new Document(PageSize.A4, 0, 0, 0, 0);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            var pdfWriter = PdfWriter.getInstance(document, out);
            document.open();
            var table = new PdfPTable(4);
            document.add(centeredParagraph(orderDetails.getName()));
            table.setWidthPercentage(100f);
            for (var front : orderDetails.getFronts()) {
                for (var i = 0; i < front.getQuantity(); i++) {
                    table.addCell(createFontCell(orderDetails.getName(), front, pdfWriter.getDirectContent()));
                }
            }
            document.add(table);
            document.close();
        } catch (DocumentException e) {
            throw new BarcodeGenerationException("Error while generating barcodes", e);
        }
        return new BarcodePdf(out);
    }

    private PdfPCell createFontCell(String orderName, FrontDto front, PdfContentByte cb) {
        var cell = new PdfPCell();
        cell.setPadding(5f);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(centeredParagraph(orderName));
        cell.addElement(centeredParagraph(format("%s x %s", front.getHeight(), front.getWidth())));
        cell.addElement(createBarcode(front, cb));
        cell.setFixedHeight(PageSize.A4.getHeight() / 10);
        return cell;
    }

    private Image createBarcode(FrontDto front, PdfContentByte cb) {
        var barcode = new Barcode39();
        barcode.setCode(String.valueOf(front.getBarcode()));
        barcode.setStartStopText(false);
        return barcode.createImageWithBarcode(cb, null, null);
    }

    private Paragraph centeredParagraph(String content) {
        var paragraph = new Paragraph(content, new Font(Font.FontFamily.UNDEFINED, 10, BOLD));
        paragraph.setAlignment(ALIGN_CENTER);
        return paragraph;
    }
}
