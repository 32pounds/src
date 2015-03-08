package com.map;
/**
 * Enum representing all the valid cardinal directions in the map
 *
 * @author Brett Menzies
 */
public enum Direction{
    NORTH((char) 0), 
    EAST ((char) 1),
    SOUTH((char) 2), 
    WEST ((char) 3);
    private char i;
    Direction(char val){ this.i = val; }
    /**
     * This provides an easy way for the enum to describe itself to commands
     * and the comm system
     */
    public char toChar(){ return i; }
}
