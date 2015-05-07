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
    private final char i;
    Direction(char val){ this.i = val; }
    /**
     * This provides an easy way for the enum to describe itself to commands
     * and the comm system
     * @return 
     */
    public char toChar(){ return i; }
    public static Direction getByChar(char in){
        switch(in){
            case 0:  return NORTH;
            case 1:  return EAST;
            case 2:  return SOUTH;
            case 3:  return WEST;
            default: return NORTH;
        }
    }
}
