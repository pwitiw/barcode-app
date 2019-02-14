package com.frontwit.barcodeapp.model;

import com.frontwit.barcodeapp.datatype.Stage;
import com.frontwit.barcodeapp.logic.IllegalProcessingOrderException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Component {

    @NotNull
    @Column(unique = true)
    private Long barcode;

    @NotNull
    private Integer height;

    @NotNull
    private Integer width;

    private String comment;

    private List<Process> processingHistory = new ArrayList<>();

    private LocalDate lastModification;

    private boolean damaged = false;

    private Integer quantity;

    public void applyProcess(Stage stage, LocalDateTime date) {
        Process process = new Process(stage, date);
        if (containsProcessAndNotFurtherProcesses(process)) {
            throw new IllegalProcessingOrderException("Component already processed on this stage and no further " +
                    "processes");
        }
        if (processingHistory.stream().anyMatch(p -> p.getStage() == Stage.DELIVERD)) {
            throw new IllegalProcessingOrderException("Component already delivered. No further processes possible.");
        }
        processingHistory.add(process);
    }

    private boolean containsProcessAndNotFurtherProcesses(Process process) {
        return processingHistory
                .stream()
                .filter(p -> Objects.equals(p, process))
                .count() >= quantity
                && processingHistory.stream().noneMatch(p -> p.getStage().greatherThan(process.getStage()));
    }

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