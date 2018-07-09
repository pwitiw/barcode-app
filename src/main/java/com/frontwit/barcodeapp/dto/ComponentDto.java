package com.frontwit.barcodeapp.dto;

import com.frontwit.barcodeapp.datatype.Barcode;
import com.frontwit.barcodeapp.model.Component;

import java.time.LocalDate;

public class ComponentDto {

    public Barcode barcode;
    public Integer height;
    public Integer width;
    public LocalDate lastModification;
    public boolean damaged;

    public static ComponentDto valueOf(Component component) {
        ComponentDto dto = new ComponentDto();
        dto.barcode = component.getBarcode();
        dto.damaged = component.isDamaged();
        dto.height = component.getHeight();
        dto.width = component.getWidth();
        dto.lastModification = component.getLastModification();
        return dto;
    }


}
