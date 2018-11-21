package com.frontwit.barcodeapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Component {

    @NotNull
    // FIXME    @Indexed(unique = true)
    private Long barcode;

    @NotNull
    private Integer height;

    @NotNull
    private Integer width;

    private String comment;

    private List<Process> processingHistory = new ArrayList<>();

    private LocalDate lastModification;

    private boolean damaged;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Component that = (Component) o;
        if (barcode == null || ((Component) o).barcode == null) {
            return false;
        }
        return Objects.equals(barcode, that.barcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(barcode);
    }

    @Override
    public String toString() {
        return height + " x " + width;
    }
}