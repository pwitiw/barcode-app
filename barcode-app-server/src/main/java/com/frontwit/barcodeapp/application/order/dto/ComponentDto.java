package com.frontwit.barcodeapp.application.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComponentDto {
    private Long id;
    private Long barcode;
    private Integer height;
    private Integer width;
    private String comment;
    private LocalDateTime lastModification;
    private String stage;
    private int quantity;
    private boolean damaged;
    private int side;
    private List<Process> processingHistory = new ArrayList();

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
