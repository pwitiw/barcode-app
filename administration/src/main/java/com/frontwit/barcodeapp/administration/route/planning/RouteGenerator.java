package com.frontwit.barcodeapp.administration.route.planning;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.itextpdf.text.pdf.BaseFont.createFont;
import static java.lang.String.format;

public class RouteGenerator {

    private static final String SETTLEMENT = "Płatność";
    private static final String NUMBER = "Lp";
    private static final String CUSTOMER = "Klient";
    private static final String ORDER = "Zamówienia";
    private static final String AMOUNT = "Kwota";
    private static final String COMMENTS = "Uwagi";
    private static final String TOTAL = "Kwota całkowita: %s zł";
    private static final String ROUTE = "Trasa: ";
    private static final String EMPTY_STRING = " ";


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
    private static final int TITLE_SIZE = 14;
    private static final int HEADER_SIZE = 13;
    private static final int BODY_SIZE = 12;
    private static final int NO_AMOUNT = 0;
    private static final int PRECISION = 2;

    byte[] generateRouteSummary(RouteDetails reportDetails) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();
        createPdf(document, reportDetails);
        document.close();
        return out.toByteArray();
    }

    private void createPdf(Document document, RouteDetails details) throws DocumentException, IOException {
        PdfName name = new PdfName(ROUTE + details.getRoute());
        document.getAccessibleAttribute(name);
        addTitle(document, details);
        addTable(document, details.getReports());
        addSummary(document, details.getReports());
    }

    private void addTitle(Document document, RouteDetails reportDetails) throws DocumentException, IOException {
        Font font = createFontWithSize(TITLE_SIZE);
        String name = reportDetails.getRoute() == null ? EMPTY_STRING : reportDetails.getRoute();
        Chunk title = new Chunk(ROUTE + name, font);
        document.add(title);
        document.add(createSpace(SPACE_SMALL));
    }

    private void addTable(Document document, List<RouteDetails.Report> reports) throws DocumentException, IOException {
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

    private void addBody(List<RouteDetails.Report> reports, PdfPTable table) throws IOException, DocumentException {
        Font font = createFontWithSize(BODY_SIZE);
        reports.forEach(report -> {
            table.addCell(String.valueOf(reports.indexOf(report) + 1));
            table.addCell(new Paragraph(report.getCustomer(), font));
            table.addCell(report.displayOrders());
            table.addCell(report.getSettlementType().getDisplayValue());
            table.addCell(report.getAmount().setScale(PRECISION, RoundingMode.HALF_EVEN).toString());
            table.addCell(EMPTY_STRING);
        });
    }

    private void addHeaders(PdfPTable table) throws IOException, DocumentException {
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

    private void addSummary(Document document, List<RouteDetails.Report> reports) throws DocumentException, IOException {
        BigDecimal total = reports.stream()
                .map(RouteDetails.Report::getAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.valueOf(NO_AMOUNT));
        Chunk chunk = new Chunk(format(TOTAL, total.setScale(PRECISION, RoundingMode.HALF_EVEN)));
        Font font = createFontWithSize(HEADER_SIZE);
        Paragraph p = new Paragraph(String.valueOf(chunk), font);
        document.add(p);
    }

    private PdfPCell createHeaderCell(String name) throws IOException, DocumentException {
        PdfPCell cell = new PdfPCell();
        Font font = createFontWithSize(HEADER_SIZE);
        cell.addElement(new Paragraph(name, font));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        return cell;
    }

    private Font createFontWithSize(int size) throws DocumentException, IOException {
        BaseFont baseFont = createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        return new Font(baseFont, size);
    }
}