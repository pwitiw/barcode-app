package com.frontwit.barcode.reader.barcode;

import com.frontwit.barcode.reader.barcode.Command;

public class CommandHandlerNotFoundException extends RuntimeException {

    public CommandHandlerNotFoundException(Class<? extends Command> aClass) {
        super(String.format("Handler for class %s not found.", aClass));
    }
}
