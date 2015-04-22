package com.comms;

import com.comms.Command;
import com.comms.GameID;
import com.comms.GameState;
import com.map.Direction;
import com.model.Player;
/**
 * This represents user commands for moving in a cardinal direction
 *
 * @author Brett Menzies
 */
public class MoveCmd extends Command{
    private Direction dir;
    private GameID actor;
    /**
     * This represents user commands for moving in a cardinal direction
     *
     * @param direction     The directon the user wishes to move in
     *                      can take value NORTH, EAST, SOUTH, or WEST
     */
    public MoveCmd(GameID target, Direction direction){
        actor = target;
        dir = direction;
    }
    public MoveCmd(){
    }
    public Direction getDirection(){
        return dir;
    }
    public GameID getActorID(){
        return actor;
    }
    public void execute(GameState state){
        Player target = (Player) state.getByID(actor);
        target.setMovingDir(dir);
    }
    public char[] getData(){
        char[] out = new char[2];
        if(dir != null){
            out[0] = actor.toChar();
            out[1] = dir.toChar();
        }
        return out;
    }
    protected void restore(char[] data){
        actor = new GameID(data[0]);
        dir = Direction.getByChar(data[1]);
    }
}
