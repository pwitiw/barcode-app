package com.frontwit.barcodeapp.server.application.order;

import com.frontwit.barcodeapp.server.application.order.dto.ComponentDto;
import com.frontwit.barcodeapp.server.application.order.dto.IllegalProcessingOrderException;
import com.frontwit.barcodeapp.server.application.order.dto.ProcessCommand;
import com.frontwit.barcodeapp.server.application.order.dto.ProcessNotAllowedException;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.*;

@Builder
// TODO zmien na front
class Component {

    @Column(unique = true)
    private Long barcode;
    private Integer height;
    private Integer width;
    private String comment;
    private List<Process> processingHistory;
    private Integer quantity;

    boolean isApplicable(ProcessCommand command) {
        return Objects.equals(barcode, command.getBarcode());
    }

    void apply(ProcessCommand command) {
        Process newProcess = new Process(Stage.valueOf(command.getStage()), command.getDate());
        long predecessors = processingHistory
                .stream()
                .filter(process -> process.isPredecessor(newProcess))
                .count();
        long succesors = processingHistory
                .stream()
                .filter(process -> process.isSuccessor(newProcess))
                .count();
        long sameStage = processingHistory
                .stream()
                .filter(process -> process.isOnSameStage(newProcess))
                .count();

        if (sameStage == quantity && succesors == 0) {
            throw new ProcessNotAllowedException();
        }
        if (predecessors == 0 && command.getStage() != 1) {
            throw new IllegalProcessingOrderException();
        }
        processingHistory.add(newProcess);
    }

    boolean isComplete() {
        Stage latestStage = processingHistory
                .stream()
                .map(Process::getStage)
                .reduce((s1, s2) -> s2.difference(s1) > 0 ? s1 : s2)
                .orElse(null);
        long latestStageNr = processingHistory
                .stream()
                .map(Process::getStage)
                .filter(stage -> Objects.equals(latestStage, stage))
                .count();
        return latestStageNr == quantity;
    }


    public ComponentDto dto() {
        Process p = processingHistory.get(processingHistory.size() - 1);
        return ComponentDto.builder()
                .barcode(barcode)
                .height(height)
                .width(width)
                .comment(comment)
                .quantity(quantity)
                .stage(p.getStage().toString())
                .lastModification(p.getDate())
                .build();

    }

    private class Process {
        @Getter
        private Stage stage;
        @Getter
        private LocalDateTime date;

        Process(Stage stage, LocalDateTime date) {
            this.stage = stage;
            this.date = date;
        }

        boolean isOnSameStage(Process process) {
            return stage == process.stage;
        }

        boolean isPredecessor(Process process) {
            return stage.difference(process.stage) == -1;
        }

        boolean isSuccessor(Process process) {
            return stage.difference(process.stage) == 1;
        }
    }
}