package com.frontwit.barcodeapp.administration.catalogue.routes.summary;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import static java.lang.String.format;

@Service
public class RouteSummary {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(RouteSummary.class.getName());
    private static final String TOTAL = "Kwota całkowita: %s zł";
    private static final String ROUTE = "Trasa: ";
    private static final String DRIVER = "Kierowca: ";
    private static final String DATE = "Data: ";
    private static final String EMPTY_STRING = "";
    private static final int SPACE_SMALL = 5;
    private static final int SPACE_BIG = 10;
    private static final int TITLE_SIZE = 14;
    private static final int HEADER_SIZE = 13;
    private static final int NO_AMOUNT = 0;

    private final RoutePdfParts pdfParts = new RoutePdfParts();
    private final RouteTable routeTable = new RouteTable(pdfParts);

    public byte[] create(RouteDetails reportDetails) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, out);
            document.open();
            createPdf(document, reportDetails);
            document.close();
        } catch (DocumentException e) {
            LOG.warn("Problem with pdfParts document creation occurred {}", reportDetails);
            throw new RouteGenerationException(e);
        }
        return out.toByteArray();
    }

    private void createPdf(Document document, RouteDetails details) throws DocumentException {
        addTitle(document, details);
        routeTable.addTable(document, details.getReports());
        addSummary(document, details.getReports());
    }

    private void addTitle(Document document, RouteDetails details) throws DocumentException {
        var name = details.getRoute() == null ? EMPTY_STRING : details.getRoute();
        var title = pdfParts.createParagraph(DATE + date(details) + ",  " + ROUTE + name + ",  " + DRIVER, TITLE_SIZE);
        document.add(title);
        document.add(pdfParts.createSpace(SPACE_SMALL));
    }

    private String date(RouteDetails details) {
        if (details.getDate() == null) {
            return EMPTY_STRING;
        }
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                .format(LocalDate.ofInstant(details.getDate(), ZoneId.of("Europe/Paris")));
    }

    private void addSummary(Document document, List<RouteDetails.Report> reports) throws DocumentException {
        document.add(pdfParts.createSpace(SPACE_BIG));
        BigDecimal total = reports.stream()
                .map(RouteDetails.Report::getAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.valueOf(NO_AMOUNT));
        Chunk chunk = new Chunk(format(TOTAL, total.setScale(2, RoundingMode.HALF_EVEN)));
        Paragraph p = pdfParts.createParagraph(String.valueOf(chunk), HEADER_SIZE);
        document.add(p);
    }
}
