package com.frontwit.barcodeapp.administration.application.qr;

import com.frontwit.barcodeapp.administration.catalogue.orders.OrderCommand;
import com.frontwit.barcodeapp.administration.catalogue.orders.barcodes.BarcodePdf;
import com.frontwit.barcodeapp.administration.domain.qr.QrCodeGenerator;
import com.frontwit.barcodeapp.administration.domain.qr.QrCodeInfoProvider;
import com.frontwit.barcodeapp.administration.domain.qr.QrCodePrinter;
import com.frontwit.barcodeapp.administration.processing.front.model.FrontProcessed;
import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import static com.frontwit.barcodeapp.administration.processing.shared.Stage.PACKING;
import static java.lang.String.format;

@RequiredArgsConstructor
@Service
public class QrCodePrintingHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCommand.class);

    private final QrCodeInfoProvider qrCodeInfoProvider;
    private final QrCodePrinter qrCodePrinter;
    private final QrCodeGenerator qrCodeGenerator;

    @EventListener
    public void handle(FrontProcessed event) {
        if (event.getStage() != PACKING) {
            return;
        }
        var pdf = qrCodeInfoProvider.find(event.getBarcode())
                .map(qrCodeGenerator::create)
                .orElse(createErrorMsgPage(event.getBarcode()));
        qrCodePrinter.print(pdf);
    }

    private BarcodePdf createErrorMsgPage(Barcode barcode) {
        var errorMsg = "Wystąpił błąd. Zeskanuj ponownie front %s";
        LOGGER.warn("Could not find QR code info for barcode {}", barcode);
        return qrCodeGenerator.createMessage(barcode, format(errorMsg, barcode.getBarcode()));
    }
}

