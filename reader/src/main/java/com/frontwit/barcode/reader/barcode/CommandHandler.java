package com.frontwit.barcode.reader.barcode;

public interface CommandHandler<T extends Command> {

    Class<T> getType();

    void handle(T command);
}
