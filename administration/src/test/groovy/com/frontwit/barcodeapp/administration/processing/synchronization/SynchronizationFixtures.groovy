package com.frontwit.barcodeapp.administration.processing.synchronization

import com.frontwit.barcodeapp.api.shared.Barcode
import com.frontwit.barcodeapp.api.shared.Quantity

class SynchronizationFixtures {
    static TargetFront aTargetFront(Barcode barcode, int quantity, int height, int width, String comment = "") {
        return new TargetFront(barcode, new Quantity(quantity), new TargetFront.Dimensions(height, width), comment)
    }

}
