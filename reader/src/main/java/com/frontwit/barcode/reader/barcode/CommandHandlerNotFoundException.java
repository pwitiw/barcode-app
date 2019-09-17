package com.frontwit.barcode.reader.barcode;

public class CommandHandlerNotFoundException extends RuntimeException {

    public CommandHandlerNotFoundException(Class<? extends Event> aClass) {
        super(String.format("Handler for class %s not found.", aClass));
    }
}
