package com.frontwit.barcodeapp.zzzdeprecated;

import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code93Writer;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

import static com.google.zxing.BarcodeFormat.CODE_93;

@Service
@Deprecated
public class BarcodeEncoder {

    Writer writer;

    public BarcodeEncoder() {
        writer = new Code93Writer();

    }

    public BufferedImage encode(String content, int imgWidth, int imgHeight) {
        try {
            BitMatrix encodedBarcodeMatrix = writer.encode(content, CODE_93, imgWidth, imgHeight / 2);
            return MatrixToImageWriter.toBufferedImage(encodedBarcodeMatrix);
        } catch (WriterException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }


}
