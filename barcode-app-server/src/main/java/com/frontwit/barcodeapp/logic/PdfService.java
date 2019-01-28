package com.frontwit.barcodeapp.logic;

import com.frontwit.barcodeapp.dto.ComponentDto;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public final class PdfService {
    private static final Logger LOG = LoggerFactory.getLogger(PdfService.class);
    private static final int STAMP_HEIGHT = 31;     //in mm
    private static final int STAMP_WIDTH = 70;      //in mm
    private static final int BARCODE_HEIGHT = (int) (0.55 * STAMP_HEIGHT);
    private static final int MARGIN = 1;
    private static final String IMAGE_FORMAT = "jpeg";

    @Autowired
    BarcodeEncoder barcodeEncoder;

    public PdfService() {
    }

    public byte[] generatePdf(final Long barcode, final Collection<ComponentDto> components) {
        Collection<byte[]> barcodeBytes = getBarCodesAsByteArrays(barcode, components);
        return createPdfForBytes(barcode, barcodeBytes);
    }

    private byte[] createPdfForBytes(Long barcode, Collection<byte[]> barCodes) {
        Document pdfDoc = new Document();
        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(pdfDoc, fos);
            pdfDoc.open();
            pdfDoc.addTitle(barcode.toString());
            pdfDoc.add(new Paragraph("Zamówienie" + barcode));
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

    private Collection<byte[]> getBarCodesAsByteArrays(final Long barcode, final Collection<ComponentDto> components) {
        components.addAll(components);
        components.addAll(components);

        return Stream.of(components.iterator().next(), components.iterator().next())
                .map(c -> createBarCodeAsByteArray(c))
                .collect(Collectors.toSet());
    }

    private byte[] createBarCodeAsByteArray(ComponentDto component) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            BufferedImage imageBarcode = barcodeEncoder.encode(component.getBarcode().toString(),
                    STAMP_WIDTH - 2 * MARGIN,
                    BARCODE_HEIGHT);

            BufferedImage image = new BufferedImage(STAMP_WIDTH, STAMP_HEIGHT, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = image.createGraphics();
            graphics2D.setBackground(Color.WHITE);

            graphics2D.fillRect(0, 0, STAMP_WIDTH, STAMP_HEIGHT);
            graphics2D.drawImage(imageBarcode, null, MARGIN, MARGIN);
            graphics2D.drawRect(0, 0, STAMP_WIDTH, STAMP_HEIGHT);


            graphics2D.dispose();
            ImageIO.write(image, IMAGE_FORMAT, byteArrayOutputStream);


            byteArrayOutputStream.flush();
        } catch (IOException e) {
            LOG.warn(e.getMessage());
        }
        return byteArrayOutputStream.toByteArray();
    }
// private byte[] createBarCodeAsByteArray(Writer writer, Long barcode, ComponentDto component) {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        try {
//            BitMatrix matrix = writer.encode(String.valueOf(component.getBarcode()), BARCODE_FORMAT, STAMP_WIDTH / 2, BARCODE_HEIGHT);
//            BufferedImage barCode = MatrixToImageWriter.toBufferedImage(matrix);
//            BufferedImage stamp = createLabelInfo(barcode.toString(), component, barCode);
//            ImageIO.write(barCode, IMAGE_FORMAT, byteArrayOutputStream);
//
//            BufferedImage image = new BufferedImage(STAMP_WIDTH, STAMP_HEIGHT, BufferedImage.TYPE_INT_ARGB);
//
//            byteArrayOutputStream.flush();
//        } catch (WriterException | IOException e) {
//            LOG.warn(e.getMessage());
//        }
//        return byteArrayOutputStream.toByteArray();
//    }

    private BufferedImage createLabelInfo(String orderName, ComponentDto component, BufferedImage sourceImage) {

        BufferedImage image = new BufferedImage(STAMP_WIDTH, STAMP_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.fillRect(0, 0, STAMP_WIDTH, STAMP_HEIGHT);
        g2d.drawRect(0, 0, STAMP_WIDTH, BARCODE_HEIGHT);
        g2d.drawImage(sourceImage, 0, 0, null);

        g2d.setColor(Color.BLACK);
        StringDrawer.drawBarcode(g2d, String.valueOf(component.getBarcode()));
        StringDrawer.drawOrderInfo(g2d, orderName, component.toString());
        g2d.dispose();

        return image;
    }

    private static class StringDrawer {
        private static final String FONT_TYPE = "Verdana";
        private static final int TEXT_SIZE = (int) (0.1 * STAMP_HEIGHT);
        private static final Font ORDER_INFO_FONT = new Font(FONT_TYPE, Font.PLAIN, TEXT_SIZE);

        static void drawBarcode(Graphics2D g2d, final String barcode) {

            Font font = new Font(FONT_TYPE, Font.PLAIN, TEXT_SIZE);

            g2d.setFont(font);
            g2d.drawRect(0, 0, STAMP_WIDTH, STAMP_HEIGHT);
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


}
