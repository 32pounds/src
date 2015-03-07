package com.map;

public enum Direction{
    NORTH((char) 0), 
    EAST ((char) 1),
    SOUTH((char) 2), 
    WEST ((char) 3);
    private char i;
    Direction(char val){ this.i = val; }
    public char toChar(){ return i; }
}
