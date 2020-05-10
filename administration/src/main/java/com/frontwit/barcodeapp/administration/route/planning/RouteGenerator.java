package com.frontwit.barcodeapp.administration.route.planning;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static java.lang.String.format;
import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

public class RouteGenerator {

    private RouteTable routeTable;

    private static final String TOTAL = "Kwota całkowita: %s zł";
    private static final String ROUTE = "Trasa: ";
    private static final String EMPTY_STRING = "";
    private static final int SPACE_SMALL = 5;
    private static final int TITLE_SIZE = 14;
    private static final int HEADER_SIZE = 13;
    private static final int NO_AMOUNT = 0;

    public RouteGenerator(RouteTable routeTable) {
        this.routeTable = routeTable;
    }

    byte[] generateRouteSummary(RouteDetails reportDetails) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, out);
            document.open();
            createPdf(document, reportDetails);
            document.close();
        } catch (DocumentException e) {
            LOGGER.warn("Problem with document occured when isn't open yet or has been closed");
        }
        return out.toByteArray();
    }

    private void createPdf(Document document, RouteDetails details) throws DocumentException {
        PdfName name = new PdfName(ROUTE + details.getRoute());
        document.getAccessibleAttribute(name);
        addTitle(document, details);
        addTable(document, details);
        addSummary(document, details.getReports());
    }

    private void addTitle(Document document, RouteDetails reportDetails) throws DocumentException {
        String name = reportDetails.getRoute() == null ? EMPTY_STRING : reportDetails.getRoute();
        Paragraph title = routeTable.createParagraphWithName(ROUTE + name, TITLE_SIZE);
        document.add(title);
        document.add(routeTable.createSpace(SPACE_SMALL));
    }

    private void addTable(Document document, RouteDetails details) throws DocumentException {
        RouteTable routeTable = new RouteTable();
        routeTable.addTable(document, details.getReports());
    }

    private void addSummary(Document document, List<RouteDetails.Report> reports) throws DocumentException {
        BigDecimal total = reports.stream()
                .map(RouteDetails.Report::getAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.valueOf(NO_AMOUNT));
        Chunk chunk = new Chunk(format(TOTAL, total.setScale(2, RoundingMode.HALF_EVEN)));
        Paragraph p = routeTable.createParagraphWithName(String.valueOf(chunk), HEADER_SIZE);
        document.add(p);
    }
}