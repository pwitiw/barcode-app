package com.frontwit.barcodeapp.labels.pdf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontwit.barcodeapp.labels.application.LabelInfo;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.BaseFont;
import lombok.RequiredArgsConstructor;

import static com.itextpdf.text.Element.ALIGN_CENTER;
import static com.itextpdf.text.Font.BOLD;
import static com.itextpdf.text.pdf.BaseFont.createFont;
import static java.awt.Font.PLAIN;
import static java.lang.String.format;

@RequiredArgsConstructor
class ParagraphFactory {

    private final int fontSize;

    Paragraph dimensionsWithQuantity(LabelInfo.FrontInfo front) {
        String result = format("%s x %s (1/%s)", front.getDimensions().getHeight(), front.getDimensions().getWidth(), front.getQuantity());
        return centeredParagraph(result);
    }

    Paragraph orderQuantity(int quantity) {
        return centeredParagraph(format("%s szt.", quantity), fontSize);
    }

    Paragraph boldCenteredParagraph(String content, int size) {
        return centeredParagraph(content, size, BOLD);
    }

    Paragraph centeredParagraph(String content) {
        return centeredParagraph(content, fontSize);
    }

    Paragraph centeredParagraph(String content, int size) {
        return centeredParagraph(content, size, PLAIN);
    }

    Paragraph centeredParagraph(String content, int size, int fontStyle) {
        try {
            var baseFont = createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            var paragraph = new Paragraph(content, new Font(baseFont, size, fontStyle));
            paragraph.setMultipliedLeading(1.1f);
            paragraph.setAlignment(ALIGN_CENTER);
            return paragraph;
        } catch (Exception ex) {
            throw new PdfGenerationException(ex.getMessage(), ex);
        }
    }

    Paragraph qrCode(LabelInfo info) throws JsonProcessingException, BadElementException {
        var json = new ObjectMapper().writeValueAsString(info);
        var qrCode = new BarcodeQRCode(json, 1, 1, null);
        var qrCodeParagraph = new Paragraph();
        Image image = qrCode.getImage();
        image.scaleToFit(75, 75);
        image.setAlignment(ALIGN_CENTER);
        qrCodeParagraph.add(image);
        return qrCodeParagraph;
    }
}
