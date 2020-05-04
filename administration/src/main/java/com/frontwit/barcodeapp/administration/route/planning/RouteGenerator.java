package com.frontwit.barcodeapp.administration.route.planning;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;

import static com.itextpdf.text.BaseColor.BLACK;
import static com.itextpdf.text.FontFactory.COURIER;
import static com.itextpdf.text.FontFactory.getFont;
import static java.lang.String.format;

public class RouteGenerator {

    private static final String SETTLEMENT = "Płatność";
    private static final String NUMBER = "Lp";
    private static final String CUSTOMER = "Klient";
    private static final String ORDER = "Zamówienia";
    private static final String AMOUNT = "Kwota";
    private static final String COMMENTS = "Uwagi";
    private static final String TOTAL = "Kwota całkowita: %s";
    private static final String ROUTE = "Trasa: ";

    private static final int COLUMNS_NR = 6;
    private static final int NR_COL_WIDTH = 50;
    private static final int CUSTOMER_COL_WIDTH = 200;
    private static final int ORDERS_COL_WIDTH = 300;
    private static final int SETTLEMENT_COL_WIDTH = 150;
    private static final int AMOUNT_COL_WIDTH = 100;
    private static final int COMMENT_COL_WIDTH = 200;
    private static final int FULL_WIDTH = 100;
    private static final int SPACE_SMALL = 5;
    private static final int SPACE_BIG = 10;
    private static final int FONT_SIZE = 14;
    private static final int NO_AMOUNT = 0;


    byte[] generateRouteSummary(RouteDetails reportDetails) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();
        createPdf(document, reportDetails);
        document.close();
        return out.toByteArray();
    }

    private void createPdf(Document document, RouteDetails details) throws DocumentException {
        PdfName name = new PdfName("Trasa: " + details.getRoute());
        document.getAccessibleAttribute(name);
        addTitle(document, details);
        addTable(document, details.getReports());
        addSummary(document, details.getReports());
    }

    private void addTitle(Document document, RouteDetails reportDetails) throws DocumentException {
        Font font = getFont(COURIER, FONT_SIZE, BLACK);
        Chunk title = new Chunk(ROUTE + reportDetails.getRoute(), font);
        document.add(title);
        document.add(createSpace(SPACE_SMALL));
    }

    private void addTable(Document document, List<RouteDetails.Report> reports) throws DocumentException {
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
            table.addCell(report.getCustomer());
            table.addCell(report.displayOrders());
            table.addCell(report.getSettlementType().getDisplayValue());
            table.addCell(report.getAmount().toString());
            table.addCell("");
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

    private Paragraph createSpace(int size) {
        Paragraph space = new Paragraph();
        space.setSpacingAfter(size);
        return space;
    }

    private void addSummary(Document document, List<RouteDetails.Report> reports) throws DocumentException {
        BigDecimal total = reports.stream()
                .map(RouteDetails.Report::getAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.valueOf(NO_AMOUNT));
        Chunk chunk = new Chunk(format(TOTAL, total));
        document.add(chunk);
    }

    private PdfPCell createHeaderCell(String name) {
        PdfPCell cell = new PdfPCell(new Phrase(name));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        return cell;
    }
}