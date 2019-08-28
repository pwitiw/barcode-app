package com.frontwit.barcode.reader.barcode;

public interface CommandHandler<T extends Event> {

    Class<T> getType();

    void handle(T command);
}
