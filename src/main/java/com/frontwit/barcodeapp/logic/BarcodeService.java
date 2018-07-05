package com.frontwit.barcodeapp.logic;

import com.frontwit.barcodeapp.model.Component;
import com.frontwit.barcodeapp.model.Order;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code93Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class BarcodeService {
    private static final Logger LOG = LoggerFactory.getLogger(BarcodeService.class);
    private static final BarcodeFormat BARCODE_FORMAT = BarcodeFormat.CODE_93;

    private static final int STAMP_HEIGHT = 60;
    private static final int STAMP_WIDTH = 120;
    private static final int BARCODE_HEIGHT = (int) (0.55 * STAMP_HEIGHT);
    private static final String IMAGE_FORMAT = "png";

    private BarcodeService() {
    }

    public static List<byte[]> getBarCodesAsByteArrays(Order order) {
        Writer writer = new Code93Writer();
        List<byte[]> barcodeBytes = new ArrayList();
        order.getComponents()
                .forEach(component -> {
                    byte[] byteImage = createBarCodeAsByteArray(writer, order.getName(), component);
                    barcodeBytes.add(byteImage);
                });
        return barcodeBytes;
    }

    private static byte[] createBarCodeAsByteArray(Writer writer, String orderName, Component component) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            BitMatrix matrix = writer.encode(component.getBarcode().toString(), BARCODE_FORMAT, STAMP_WIDTH, BARCODE_HEIGHT);
            BufferedImage barCode = MatrixToImageWriter.toBufferedImage(matrix);

            BufferedImage stamp = createLabelInfo(orderName, component, barCode);
            ImageIO.write(stamp, IMAGE_FORMAT, baos);
            baos.flush();
        } catch (WriterException | IOException e) {
            LOG.warn(e.getMessage());
        }
        return baos.toByteArray();
    }

    private static BufferedImage createLabelInfo(String orderName, Component component, BufferedImage sourceImage) {

        BufferedImage image = new BufferedImage(STAMP_WIDTH, STAMP_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.fillRect(0, 0, STAMP_WIDTH, STAMP_HEIGHT);
        g2d.drawRect(0, 0, STAMP_WIDTH, BARCODE_HEIGHT);
        g2d.drawImage(sourceImage, 0, 0, null);

        g2d.setColor(Color.BLACK);
        StringDrawer.drawBarcode(g2d, component.getBarcode().toString());
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
}
