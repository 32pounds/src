package com.comms;

import com.comms.Command;
import com.map.Direction;

public class MoveCmd extends Command{
    private Direction dir;
    public MoveCmd(Direction direction){
        dir = direction;
    }
    public Direction getDirection(){
        return dir;
    }
    public char[] getData(){
        char[] out = new char[1];
        if(dir != null){
            out[0] = dir.toChar();   
        }
        return out;
    }
}
