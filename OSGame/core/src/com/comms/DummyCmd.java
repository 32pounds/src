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
public class DummyCmd extends Command{
    public DummyCmd(){
    }
    public void execute(GameState state){
    }
    public char[] getData(){
        return (getClass().getName()+":test").toCharArray();
    }
    protected void   restore(char[] data){

    }
}
