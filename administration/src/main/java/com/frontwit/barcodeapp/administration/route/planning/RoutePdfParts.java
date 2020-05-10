package com.frontwit.barcodeapp.administration.route.planning;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.itextpdf.text.pdf.BaseFont.createFont;

class RoutePdfParts {
    private static final Logger LOG = LoggerFactory.getLogger(RoutePdfParts.class.getName());

    Paragraph createParagraph(String name, int size) {
        BaseFont baseFont;
        try {
            baseFont = createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(baseFont, size);
            return new Paragraph(name, font);
        } catch (IOException | DocumentException e) {
            LOG.warn("Problem with font occured");
            return new Paragraph();
        }
    }

    Paragraph createSpace(int size) {
        Paragraph space = new Paragraph();
        space.setSpacingAfter(size);
        return space;
    }
}
