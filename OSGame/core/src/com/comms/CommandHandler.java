package com.comms;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Handle the command queue
 */
public class CommandHandler {

    //thread save queue
    private final ConcurrentLinkedQueue<Command> commands;

    //singleton instance
    private static CommandHandler instance;

    //private constructor to grant a singleton pattern
    private CommandHandler() {
        commands = new ConcurrentLinkedQueue<Command>();
    }

    /**
     * Get singleton instance
     * @return Singleton instance
     */
    public static CommandHandler getInstance() {
        if (instance == null)
            instance = new CommandHandler();
        return instance;
    }

    /**
     * Queue a command.
     * @param command command to be queued.
     */
    public void add(Command command) {
        commands.add(command);
    }

    /**
     * Remove the fist command of the queue
     * @return The first command of the queue
     */
    public Command remove() {
        if (!commands.isEmpty())
            return commands.remove();
        //if there is no command return null
        return null;
    }
}
