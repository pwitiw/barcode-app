package com.frontwit.barcodeapp.logic;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;

public final class PdfGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(PdfGenerator.class);

    public byte[] createPdfForBytes(String name, Collection<byte[]> barCodes) {
        Document pdfDoc = new Document();
        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(pdfDoc, fos);
            pdfDoc.open();
            pdfDoc.addTitle(name);
            pdfDoc.add(createParagraph(name));

            barCodes.forEach(bytes -> {
                Image image;
                try {
                    image = Image.getInstance(bytes);
                    pdfDoc.add(image);
                } catch (IOException | DocumentException e) {
                    LOG.warn(e.getMessage());
                }
            });

            pdfDoc.close();
        } catch (DocumentException e) {
            LOG.warn(e.getMessage());
        }
        return fos.toByteArray();
    }

    private Paragraph createParagraph(String orderName) {
        return new Paragraph(String.format("Zamówienie %s", orderName));
    }
}
