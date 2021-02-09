package com.frontwit.barcodeapp.administration.catalogue.orders.barcodes;

import com.frontwit.barcodeapp.administration.catalogue.orders.dto.FrontDto;
import com.frontwit.barcodeapp.administration.catalogue.orders.dto.OrderDetailDto;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Collection;

@Service
public class BarcodesX21PdfGenerator {
    private static final float MARGIN_BASE = 0f;
    private static final float CELL_PADDING = 20f;
    private static final int ROW_AMOUNT = 7;
    private static final int COLUMN_AMOUNT = 3;
    private static final int FONT_SIZE = 12;

    private final BarcodeCellCreator cellCreator = new BarcodeCellCreator(MARGIN_BASE, ROW_AMOUNT, CELL_PADDING, FONT_SIZE);

    public BarcodePdf createBarcodesFor(Collection<OrderDetailDto> orders) {
        Document document = new Document(PageSize.A4, MARGIN_BASE, MARGIN_BASE, MARGIN_BASE, MARGIN_BASE);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, out);
            document.open();
            var table = new PdfPTable(COLUMN_AMOUNT);
            table.setWidthPercentage(100f);
            for (var orderDetails : orders) {
                for (var front : orderDetails.getFronts()) {
                    for (var i = 0; i < front.getQuantity(); i++) {
                        table.addCell(cellCreator.cell(i + 1, orderDetails, front, elementsInOrder(orderDetails)));
                    }
                }
            }
            fillTableWithEmptyCells(orders, table);
            document.add(table);
            document.close();
        } catch (DocumentException e) {
            throw new BarcodeGenerationException("Error while generating barcodes", e);
        }
        return new BarcodePdf(out);
    }

    private void fillTableWithEmptyCells(Collection<OrderDetailDto> orders, PdfPTable table) {
        for (var i = totalElements(orders) % COLUMN_AMOUNT; i % COLUMN_AMOUNT > 0; i++) {
            table.addCell(cellCreator.emptyCell());
        }
    }

    private Integer elementsInOrder(OrderDetailDto orders) {
        return orders.getFronts().stream()
                .map(FrontDto::getQuantity)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private Integer totalElements(Collection<OrderDetailDto> orders) {
        return orders.stream()
                .map(this::elementsInOrder)
                .reduce(Integer::sum)
                .orElse(0);
    }
}

