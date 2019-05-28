package com.frontwit.barcodeapp.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
public class ComponentDto {
    private Long id;
    private Long barcode;
    private Integer height;
    private Integer width;
    private String comment;
    private LocalDate lastModification;
    private int quantity;
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
