package com.frontwit.barcodeapp.administration.catalogue.barcodes;

import com.frontwit.barcodeapp.administration.catalogue.dto.FrontDto;
import com.frontwit.barcodeapp.administration.catalogue.dto.OrderDetailDto;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import lombok.AllArgsConstructor;

import static com.itextpdf.text.Element.ALIGN_CENTER;
import static java.awt.Font.PLAIN;
import static java.lang.String.format;

@AllArgsConstructor
class BarcodeCellCreator {

    private final float baseMargin;
    private final int rows;
    private final float cellPadding;
    private final int fontSize;

    PdfPCell cell(int nr, OrderDetailDto order, FrontDto front, int totalElements) {
        var cell = emptyCell();
        cell.setPadding(cellPadding);
        cell.addElement(centeredParagraph(order.getRoute()));
        cell.addElement(centeredParagraph(order.getCustomer()));
        cell.addElement(nameWithQuantity(order.getName(), totalElements));
        cell.addElement(dimensionsWithQuantity(nr, front));
        cell.addElement(centeredParagraph(String.valueOf(front.getBarcode()), fontSize - 1));
        return cell;
    }

    PdfPCell emptyCell() {
        var cell = new PdfPCell();
        cell.setPadding(cellPadding);
        cell.setFixedHeight((float) Math.floor((PageSize.A4.getHeight() - 2 * baseMargin) / rows));
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private Paragraph nameWithQuantity(String orderName, int quantity) {
        return centeredParagraph(format("%s - %s szt.", orderName, quantity));
    }

    private Paragraph dimensionsWithQuantity(int nr, FrontDto front) {
        String result = format("%s x %s (%s/%s)", front.getHeight(), front.getWidth(), nr, front.getQuantity());
        return centeredParagraph(result);
    }

    private Paragraph centeredParagraph(String content) {
        return centeredParagraph(content, fontSize);
    }

    private Paragraph centeredParagraph(String content, int size) {
        var paragraph = new Paragraph(content, new Font(Font.FontFamily.UNDEFINED, size, PLAIN));
        paragraph.setMultipliedLeading(1.1f);
        paragraph.setAlignment(ALIGN_CENTER);
        return paragraph;
    }
}
