package com.frontwit.barcodeapp.administration.processing.application.readmodel;

import com.frontwit.barcodeapp.administration.processing.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.swing.text.ComponentView;
import java.util.List;

@AllArgsConstructor
@Getter
public class OrderDetailsView {
    private Status status;
    private List<ComponentView> missings;
}
