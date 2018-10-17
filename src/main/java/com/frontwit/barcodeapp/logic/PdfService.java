package com.frontwit.barcodeapp.logic;

import com.frontwit.barcodeapp.dto.ComponentDto;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code93Writer;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

public final class PdfService {
    private static final Logger LOG = LoggerFactory.getLogger(PdfService.class);
    private static final BarcodeFormat BARCODE_FORMAT = BarcodeFormat.CODE_93;
    private static final int STAMP_HEIGHT = 60;
    private static final int STAMP_WIDTH = 120;
    private static final int BARCODE_HEIGHT = (int) (0.55 * STAMP_HEIGHT);
    private static final String IMAGE_FORMAT = "png";

    public PdfService() {
    }

    public byte[] generatePdf(final String name, final Collection<ComponentDto> components) {
        Collection<byte[]> barcodeBytes = getBarCodesAsByteArrays(name, components);
        return createPdfForBytes(name, barcodeBytes);
    }

    private Collection<byte[]> getBarCodesAsByteArrays(final String name, final Collection<ComponentDto> components) {
        Writer writer = new Code93Writer();
        return components.stream()
                .map(c -> createBarCodeAsByteArray(writer, name, c))
                .collect(Collectors.toSet());
    }

    private byte[] createBarCodeAsByteArray(Writer writer, String orderName, ComponentDto component) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            BitMatrix matrix = writer.encode(String.valueOf(component.barcode), BARCODE_FORMAT, STAMP_WIDTH, BARCODE_HEIGHT);
            BufferedImage barCode = MatrixToImageWriter.toBufferedImage(matrix);

            BufferedImage stamp = createLabelInfo(orderName, component, barCode);
            ImageIO.write(stamp, IMAGE_FORMAT, baos);
            baos.flush();
        } catch (WriterException | IOException e) {
            LOG.warn(e.getMessage());
        }
        return baos.toByteArray();
    }

    private BufferedImage createLabelInfo(String orderName, ComponentDto component, BufferedImage sourceImage) {

        BufferedImage image = new BufferedImage(STAMP_WIDTH, STAMP_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.fillRect(0, 0, STAMP_WIDTH, STAMP_HEIGHT);
        g2d.drawRect(0, 0, STAMP_WIDTH, BARCODE_HEIGHT);
        g2d.drawImage(sourceImage, 0, 0, null);

        g2d.setColor(Color.BLACK);
        StringDrawer.drawBarcode(g2d, String.valueOf(component.barcode));
        StringDrawer.drawOrderInfo(g2d, orderName, component.toString());
        g2d.dispose();

        return image;
    }

    private static class StringDrawer {
        private static final String FONT_TYPE = "Arial";
        private static final int TEXT_SIZE = (int) (0.15 * STAMP_HEIGHT);
        private static final Font BARCODE_FONT = new Font(FONT_TYPE, Font.PLAIN, (int) (TEXT_SIZE));
        private static final Font ORDER_INFO_FONT = new Font(FONT_TYPE, Font.PLAIN, TEXT_SIZE);

        static void drawBarcode(Graphics2D g2d, final String barcode) {
            g2d.setFont(BARCODE_FONT);
            FontMetrics barcodeFm = g2d.getFontMetrics();
            FontMetrics orderInfoFm = new Canvas().getFontMetrics(ORDER_INFO_FONT);
            int x = ((STAMP_WIDTH - barcodeFm.stringWidth(barcode)) / 2);
            int y = STAMP_HEIGHT - orderInfoFm.getHeight();
            g2d.setColor(Color.BLACK);
            g2d.drawString(barcode, x, y);
        }

        static void drawOrderInfo(Graphics2D g2d, final String orderName, final String barcode) {
            String orderInfo = orderName + "  " + barcode;
            g2d.setFont(ORDER_INFO_FONT);
            FontMetrics fm = g2d.getFontMetrics();
            int x = ((STAMP_WIDTH - fm.stringWidth(orderInfo)) / 2);
            g2d.setColor(Color.BLACK);
            g2d.drawString(orderInfo, x, STAMP_HEIGHT);
        }
    }

    private byte[] createPdfForBytes(String name, Collection<byte[]> barCodes) {
        Document pdfDoc = new Document();
        org.apache.commons.io.output.ByteArrayOutputStream fos = new org.apache.commons.io.output.ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(pdfDoc, fos);
            pdfDoc.open();
            pdfDoc.addTitle(name);
            pdfDoc.add(createParagraph(name));

            barCodes.forEach(bytes -> {
                com.itextpdf.text.Image image;
                try {
                    image = com.itextpdf.text.Image.getInstance(bytes);
                    pdfDoc.add(image);
                } catch (IOException | DocumentException e) {
                    LOG.warn(e.getMessage());
                }
            });

            pdfDoc.close();
        } catch (DocumentException e) {
            LOG.warn(e.getMessage());
        }
        return fos.toByteArray();
    }

    private Paragraph createParagraph(String orderName) {
        return new Paragraph(String.format("Zamówienie %s", orderName));
    }
}
