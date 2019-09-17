package com.frontwit.barcodeapp.administration.order.processing.front.application.readmodel;

import com.frontwit.barcodeapp.administration.order.processing.shared.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.swing.text.ComponentView;
import java.util.List;

@AllArgsConstructor
@Getter
public class OrderDetailsView {
    private Stage stage;
    private List<ComponentView> missings;
}
