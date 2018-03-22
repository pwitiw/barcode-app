package com.frontwit.barcodeapp.logic;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code93Writer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class BarCodeGenerator {

    private final static BarcodeFormat BARCODE_FORMAT = BarcodeFormat.CODE_93;
    private final static int BARCODE_WIDTH = 45;
    private final static int BARCODE_HEIGHT = 45;
    private final static String IMAGE_FORMAT = "jpg";

    private BarCodeGenerator() {
    }

    public static List<byte[]> getBarCodesAsByteArrays(Collection<? extends Number> barcodes) {
        Writer writer = new Code93Writer();
        List<byte[]> barcodeBytes = new ArrayList();
        barcodes.stream()
                .map(Object::toString)
                .forEach(barcode -> {
                    byte[] byteImage = createBarCodeAsByteArray(writer, barcode);
                    barcodeBytes.add(byteImage);
                });
        return barcodeBytes;
    }

    private static byte[] createBarCodeAsByteArray(Writer writer, String barcode) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            BitMatrix matrix = writer.encode(barcode, BARCODE_FORMAT, BARCODE_WIDTH, BARCODE_HEIGHT);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);

            ImageIO.write(bufferedImage, IMAGE_FORMAT, baos);
            baos.flush();
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }
}
