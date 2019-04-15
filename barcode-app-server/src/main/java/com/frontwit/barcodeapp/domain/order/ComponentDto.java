package com.frontwit.barcodeapp.domain.order;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class ComponentDto {

    private Long barcode;
    private Integer height;
    private Integer width;
    private String comment;
    private LocalDate lastModification;
    private boolean damaged;
    private List<Process> processingHistory = new ArrayList();

    private ComponentDto() {
    }

    public ComponentDto(Integer height, Integer width, String comment) {
        this.height = height;
        this.width = width;
        this.comment = comment;
        this.lastModification = LocalDate.now();
        this.damaged = false;
    }

//    public static ComponentDto valueOf(Component component) {
//        ComponentDto dto = new ComponentDto();
//        dto.barcode = component.getBarcode();
//        dto.damaged = component.isDamaged();
//        dto.height = component.getHeight();
//        dto.width = component.getWidth();
//        dto.lastModification = component.getLastModification();
//        dto.processingHistory.addAll(component.getProcessingHistory());
//        return dto;
//    }
//
//    public Component toEntity() {
//        Component component = new Component();
//        component.setWidth(width);
//        component.setHeight(height);
//        component.setDamaged(damaged);
//        component.setLastModification(lastModification);
//        component.setComment(comment);
//        component.setProcessingHistory(processingHistory);
//        return component;
//    }
//
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentDto that = (ComponentDto) o;
        if (barcode == null || ((ComponentDto) o).barcode == null) {
            return false;
        }
        return Objects.equals(barcode, that.barcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(barcode);
    }
}
