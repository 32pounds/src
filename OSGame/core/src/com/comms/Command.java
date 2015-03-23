package com.comms;

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
     *
     * Eventually we might have a "claim data"
     * method that will parse a char array and return true if this
     * class made it. Just an idea though.
     *
     */
    abstract public char[] getData();
    abstract public void execute();
}
