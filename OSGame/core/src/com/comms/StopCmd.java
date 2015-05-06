package com.comms;

import com.comms.Command;
import com.comms.GameID;
import com.comms.GameState;
import com.map.Direction;
import com.model.Player;
import com.model.Entity;
/**
 * This represents user commands for moving in a cardinal direction
 *
 * @author Brett Menzies
 */
public class StopCmd extends Command{
    private Direction dir;
    private GameID actor;
    /**
     * This represents user commands for moving in a cardinal direction
     *
     * @param direction     The directon the user wishes to stop moving in
     *                      can take value NORTH, EAST, SOUTH, or WEST
     */
    public StopCmd(GameID target, Direction direction){
        actor = target;
        dir = direction;
    }
    public StopCmd(){

    }
    public Direction getDirection(){
        return dir;
    }
    public GameID getActorID(){
        return actor;
    }
    public void execute(GameState state){
        Entity target = state.getByID(actor);
        if(target.getClass() == Player.class){
            ((Player)target).stopMoving(dir);
        } else {
            System.err.println("Received non-player target ID in StopCmd ID was "+actor.toString());
        }
    }
    public char[] getData(){
        char[] gameID = (actor.toString()).toCharArray();
        char[] out = new char[gameID.length+1];
        out[0] = dir.toChar();
        for(int i=1; i<=gameID.length; i++){
            out[i] = gameID[i-1];
        }
        return out;
    }
    protected void restore(String data){
        String gameID = data.substring(1, data.length());
        gameID = gameID.substring(0, gameID.indexOf('\0'));
        actor = new GameID(gameID);
        dir = Direction.getByChar(data.charAt(0));
    }
}
