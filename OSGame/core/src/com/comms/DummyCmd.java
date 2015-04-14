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
    public char[] getData(){
        return new char[0];
    }
    public void execute(GameState state){
    }
}
