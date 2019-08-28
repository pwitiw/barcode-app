package com.frontwit.barcode.reader.barcode;

import java.util.HashMap;
import java.util.Map;

public class CommandGateway {

    private final Map<Class<? extends Event>, CommandHandler> handlers = new HashMap<>();

    public CommandHandler register(CommandHandler commandHandler) {
        if (handlers.containsKey(commandHandler.getType())) {
            return handlers.get(commandHandler.getType());
        }
        return handlers.put(commandHandler.getType(), commandHandler);
    }

    public void fire(Event event) {
        CommandHandler handler = handlers.get(event.getClass());
        if (handler == null) {
            throw new CommandHandlerNotFoundException(event.getClass());
        }
        handler.handle(event);
    }

}
