package com.frontwit.barcodeapp.administration.route.planning;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.IOException;
import java.math.RoundingMode;
import java.util.List;

import static com.itextpdf.text.pdf.BaseFont.createFont;
import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

class RouteTable {

    private static final String SETTLEMENT = "Płatność";
    private static final String NUMBER = "Lp";
    private static final String CUSTOMER = "Klient";
    private static final String ORDER = "Zamówienia";
    private static final String AMOUNT = "Kwota";
    private static final String COMMENTS = "Uwagi";
    private static final String EMPTY_STRING = "";

    private static final int COLUMNS_NR = 6;
    private static final int NR_COL_WIDTH = 50;
    private static final int CUSTOMER_COL_WIDTH = 200;
    private static final int ORDERS_COL_WIDTH = 300;
    private static final int SETTLEMENT_COL_WIDTH = 150;
    private static final int AMOUNT_COL_WIDTH = 100;
    private static final int COMMENT_COL_WIDTH = 200;
    private static final int FULL_WIDTH = 100;
    private static final int SPACE_BIG = 10;
    private static final int BODY_SIZE = 12;
    private static final int HEADER_SIZE = 13;

    void addTable(Document document, List<RouteDetails.Report> reports) throws DocumentException {
        PdfPTable table = new PdfPTable(COLUMNS_NR);
        table.setTotalWidth(new float[]{
                NR_COL_WIDTH,
                CUSTOMER_COL_WIDTH,
                ORDERS_COL_WIDTH,
                SETTLEMENT_COL_WIDTH,
                AMOUNT_COL_WIDTH,
                COMMENT_COL_WIDTH});
        table.setWidthPercentage(FULL_WIDTH);
        addHeaders(table);
        addBody(reports, table);
        document.add(table);
        document.add(createSpace(SPACE_BIG));
    }

    private void addBody(List<RouteDetails.Report> reports, PdfPTable table) {
        reports.forEach(report -> {
            table.addCell(String.valueOf(reports.indexOf(report) + 1));
            table.addCell(createParagraphWithName(report.getCustomer(), BODY_SIZE));
            table.addCell(report.displayOrders());
            table.addCell(report.getSettlementType().getDisplayValue());
            table.addCell(report.getAmount().setScale(2, RoundingMode.HALF_EVEN).toString());
            table.addCell(EMPTY_STRING);
        });
    }

    private void addHeaders(PdfPTable table) {
        table.addCell(createHeaderCell(NUMBER));
        table.addCell(createHeaderCell(CUSTOMER));
        table.addCell(createHeaderCell(ORDER));
        table.addCell(createHeaderCell(SETTLEMENT));
        table.addCell(createHeaderCell(AMOUNT));
        table.addCell(createHeaderCell(COMMENTS));
    }

    private PdfPCell createHeaderCell(String name) {
        PdfPCell cell = new PdfPCell();
        cell.addElement(createParagraphWithName(name, HEADER_SIZE));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        return cell;
    }

    Paragraph createParagraphWithName(String name, int size) {
        BaseFont baseFont = null;
        try {
            baseFont = createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        } catch (IOException | DocumentException e) {
            LOGGER.warn("Problem with font occured");
        }
        Font font = new Font(baseFont, size);
        return new Paragraph(name, font);
    }

    Paragraph createSpace(int size) {
        Paragraph space = new Paragraph();
        space.setSpacingAfter(size);
        return space;
    }
}
