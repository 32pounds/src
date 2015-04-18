package com.comms;
import com.comms.GameState;

/**
 * abstract class for abstract commands representing the users actions
 * These are intended to be constructed by the input system
 * then tranported by the comms system before being sent to the
 * main game logic loop to process
 * @author Brett Menzies
 */
public abstract class Command{
    /**
     * Returns all the data necessary to describe itself in a compact format
     * for the comms system.
     */
    abstract public char[] getData();
    abstract public void execute(GameState state);
}
