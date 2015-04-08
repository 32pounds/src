package com.comms;

import com.comms.Command;
import com.map.Direction;
import com.model.Player;
/**
 * This represents user commands for moving in a cardinal direction
 *
 * @author Brett Menzies
 */
public class StopCmd extends Command{
    private Direction dir;
    private Player actor;
    /**
     * This represents user commands for moving in a cardinal direction
     *
     * @param direction     The directon the user wishes to move in
     *                      can take value NORTH, EAST, SOUTH, or WEST
     */
    public StopCmd(Player target, Direction nulled){
        actor = target;
        dir = nulled;
    }
    public char[] getData(){
        char[] out = new char[1];
        if(dir != null){
            out[0] = dir.toChar();
        }
        return out;
    }
    public void execute(){
        actor.stopMoving(dir);
    }
}
