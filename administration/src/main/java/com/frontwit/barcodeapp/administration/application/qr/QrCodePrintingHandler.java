package com.frontwit.barcodeapp.administration.application.qr;

import com.frontwit.barcodeapp.administration.catalogue.orders.OrderCommand;
import com.frontwit.barcodeapp.administration.domain.qr.QrCodeGenerator;
import com.frontwit.barcodeapp.administration.domain.qr.QrCodeInfoProvider;
import com.frontwit.barcodeapp.administration.domain.qr.QrCodePrinter;
import com.frontwit.barcodeapp.administration.processing.front.model.FrontProcessed;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import static com.frontwit.barcodeapp.administration.processing.shared.Stage.PACKING;

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
                .map(qrCodeGenerator::create);
        if (pdf.isPresent()) {
            LOGGER.warn("Printing label... barcode={}", event.getBarcode());
            qrCodePrinter.print(pdf.get());
        } else {
            LOGGER.warn("Could not find QR code info for barcode {}", event.getBarcode());
        }
    }
}

