package com.frontwit.barcodeapp.administration.infrastructure.pdf;

import com.frontwit.barcodeapp.administration.catalogue.orders.barcodes.BarcodePdf;
import com.frontwit.barcodeapp.administration.domain.qr.QrCodeGenerator;
import com.frontwit.barcodeapp.administration.domain.qr.QrCodeInfo;
import com.frontwit.barcodeapp.administration.domain.qr.QrCodePreparationException;
import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@RequiredArgsConstructor
@Service
public class QrCodePdfGenerator implements QrCodeGenerator {
    public static final int FONT_SIZE = 12;
    private final ParagraphFactory paragraphFactory = new ParagraphFactory(FONT_SIZE);

    @Override
    public BarcodePdf createMessage(Barcode barcode, String message) {
        try {
            Document document = new Document(new Rectangle(170, 170), 1, 1, 1, 1);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(paragraphFactory.centeredParagraph(message));
            document.close();
            return new BarcodePdf(barcode, out);
        } catch (DocumentException e) {
            throw new QrCodePreparationException("Error while generating pdf", e);
        }
    }

    @Override
    public BarcodePdf create(QrCodeInfo info) {
        try {
            final QrCodeInfo.QrOrder order = info.getOrder();
            final QrCodeInfo.QrFront front = info.getFront();
            Document document = new Document(new Rectangle(170, 170), 1, 1, 1, 1);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(paragraphFactory.boldCenteredParagraph(order.getName(), FONT_SIZE + 8));
            document.add(paragraphFactory.orderQuantity(order.getQuantity()));
            document.add(paragraphFactory.centeredParagraph(order.getRoute()));
            document.add(paragraphFactory.centeredParagraph(order.getCustomer()));
            document.add(paragraphFactory.dimensionsWithQuantity(front));
            document.add(paragraphFactory.qrCode(info));
            document.add(paragraphFactory.centeredParagraph(String.valueOf(front.getBarcode()), FONT_SIZE - 3));
            document.close();
            return new BarcodePdf(new Barcode(front.getBarcode()), out);
        } catch (Exception e) {
            throw new QrCodePreparationException("Error while generating barcodes", e);
        }
    }

}
