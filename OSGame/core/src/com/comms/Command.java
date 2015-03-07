package com.comms;

//abstract commands for input to create and pass through comms to game loop
public abstract class Command{
    //this is a class so that we can add implementations here
    //in the future that are common to all command messages
    abstract public char[] getData();
}
