package com.frontwit.barcodeapp.administration.route.planning;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static java.lang.String.format;


public class RouteGenerator {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(RouteGenerator.class.getName());
    private static final String TOTAL = "Kwota całkowita: %s zł";
    private static final String ROUTE = "Trasa: ";
    private static final String EMPTY_STRING = "";
    private static final int SPACE_SMALL = 5;
    private static final int SPACE_BIG = 10;
    private static final int TITLE_SIZE = 14;
    private static final int HEADER_SIZE = 13;
    private static final int NO_AMOUNT = 0;

    private final RouteTable routeTable;
    private final RoutePdfParts routePdfParts;

    RouteGenerator(RouteTable routeTable,
                   RoutePdfParts routePdfParts) {
        this.routeTable = routeTable;
        this.routePdfParts = routePdfParts;
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
            LOG.warn("Problem with pdf document creation occurred {}", reportDetails);
            throw new RouteGenerationException();
        }
        return out.toByteArray();
    }

    private void createPdf(Document document, RouteDetails details) throws DocumentException {
        PdfName name = new PdfName(ROUTE + details.getRoute());
        document.getAccessibleAttribute(name);
        addTitle(document, details);
        routeTable.addTable(document, details.getReports());
        addSummary(document, details.getReports());
    }

    private void addTitle(Document document, RouteDetails reportDetails) throws DocumentException {
        String name = reportDetails.getRoute() == null ? EMPTY_STRING : reportDetails.getRoute();
        Paragraph title = routePdfParts.createParagraphWithName(ROUTE + name, TITLE_SIZE);
        document.add(title);
        document.add(routePdfParts.createSpace(SPACE_SMALL));
    }

    private void addSummary(Document document, List<RouteDetails.Report> reports) throws DocumentException {
        document.add(routePdfParts.createSpace(SPACE_BIG));
        BigDecimal total = reports.stream()
                .map(RouteDetails.Report::getAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.valueOf(NO_AMOUNT));
        Chunk chunk = new Chunk(format(TOTAL, total.setScale(2, RoundingMode.HALF_EVEN)));
        Paragraph p = routePdfParts.createParagraphWithName(String.valueOf(chunk), HEADER_SIZE);
        document.add(p);
    }
}