package com.comms;

import java.util.concurrent.ConcurrentLinkedQueue;

public class CommandHandler {

    //thread save queue
    private ConcurrentLinkedQueue<Command> commands;

    private static CommandHandler instance;

    private CommandHandler() {
        commands = new ConcurrentLinkedQueue<Command>();
    }

    public static CommandHandler getInstance() {
        if (instance == null)
            instance = new CommandHandler();
        return instance;
    }

    public void add(Command command) {
        commands.add(command);
    }

    public Command remove() {
        if (commands.size() != 0)
            return commands.remove();
        return null;
    }
}
