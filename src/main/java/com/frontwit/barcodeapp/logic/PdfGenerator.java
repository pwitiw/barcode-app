package com.frontwit.barcodeapp.logic;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.IOException;
import java.util.List;

public final class PdfGenerator {

    private PdfGenerator() {
    }

    //TODO patryk clean these exceptions
    public static byte[] createPdfAsBytes(String orderName, List<byte[]> barCodes) throws DocumentException {
        Document pdfDoc = new Document();
        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        PdfWriter.getInstance(pdfDoc, fos);
        pdfDoc.open();
        pdfDoc.addTitle(orderName);
        pdfDoc.add(createParagraph(orderName));

        barCodes.forEach(bytes -> {
            Image image = null;
            try {
                image = Image.getInstance(bytes);
                pdfDoc.add(image);
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }
        });

        pdfDoc.close();
        return fos.toByteArray();
    }

    private static Paragraph createParagraph(String orderName) {
        return new Paragraph(String.format("Zamówienie %s", orderName));
    }
}
