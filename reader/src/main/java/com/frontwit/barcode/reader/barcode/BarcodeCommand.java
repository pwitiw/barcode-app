package com.frontwit.barcode.reader.barcode;

import java.time.LocalDateTime;
import java.util.Objects;

public class BarcodeCommand implements Command {

    private Long id;
    private final Integer readerId;
    private final Long barcode;
    private final LocalDateTime date;

    public BarcodeCommand(Integer readerId, Long barcode) {
        this(null, readerId, barcode, LocalDateTime.now());
    }

    public BarcodeCommand(Long id, Integer readerId, Long barcode, LocalDateTime date) {
        this.id = id;
        this.readerId = readerId;
        this.barcode = barcode;
        this.date = date;
    }

    public Integer getReaderId() {
        return readerId;
    }

    public Long getBarcode() {
        return barcode;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "BarcodeCommand: reader=" + readerId + ", barcode=" + barcode + ", timestamp= " + date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BarcodeCommand that = (BarcodeCommand) o;
        return Objects.equals(readerId, that.readerId) &&
                Objects.equals(barcode, that.barcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(readerId, barcode);
    }
}
