package com.frontwit.barcodeapp.administration.sychronization

import com.frontwit.barcodeapp.administration.processing.shared.Barcode
import com.frontwit.barcodeapp.administration.processing.shared.Quantity
import com.frontwit.barcodeapp.administration.processing.synchronization.TargetFront

class SynchronizationFixtures {
    static TargetFront aTargetFront(Barcode barcode, int quantity, int height, int width, String comment = "") {
        return new TargetFront(barcode, new Quantity(quantity), new TargetFront.Dimensions(height, width), comment)
    }

}
