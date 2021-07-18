package com.frontwit.barcodeapp.labels.pdf;

import com.frontwit.barcodeapp.api.shared.Barcode;
import com.frontwit.barcodeapp.labels.application.LabelGenerator;
import com.frontwit.barcodeapp.labels.application.LabelInfo;
import com.frontwit.barcodeapp.labels.application.QrCodePreparationException;
import com.frontwit.barcodeapp.labels.application.LabelResult;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@RequiredArgsConstructor
@Service
public class LabelPdfGenerator implements LabelGenerator {
    public static final int FONT_SIZE = 12;
    private final ParagraphFactory paragraphFactory = new ParagraphFactory(FONT_SIZE);

    @Override
    public LabelResult createMessage(Barcode barcode, String message) {
        try {
            Document document = new Document(new Rectangle(170, 170), 1, 1, 1, 1);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(paragraphFactory.centeredParagraph(message));
            document.close();
            return new LabelResult(barcode.getBarcode(), out);
        } catch (DocumentException e) {
            throw new QrCodePreparationException("Error while generating pdf", e);
        }
    }

    @Override
    public LabelResult create(LabelInfo info) {
        try {
            final LabelInfo.OrderInfo order = info.getOrder();
            final LabelInfo.FrontInfo front = info.getFront();
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
            return new LabelResult(front.getBarcode(), out);
        } catch (Exception e) {
            throw new QrCodePreparationException("Error while generating barcodes", e);
        }
    }

}
