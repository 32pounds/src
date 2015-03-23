package com.comms;

import com.comms.Command;
import com.map.Direction;
import com.model.Player;
/**
 * This represents user commands for moving in a cardinal direction
 *
 * @author Brett Menzies
 */
public class MoveCmd extends Command{
    private Direction dir;
    private Player actor;
    /**
     * This represents user commands for moving in a cardinal direction
     *
     * @param direction     The directon the user wishes to move in
     *                      can take value NORTH, EAST, SOUTH, or WEST
     */
    public MoveCmd(Player target, Direction direction){
        dir = direction;
        actor = target;
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
    public void execute(){
        actor.move(dir);
    }
}
