package com.comms;

/**
 * This represents user commands for moving in a cardinal direction
 *
 * @author Brett Menzies
 */
public class DummyCmd extends Command{
    public DummyCmd(){
    }
    @Override
    public void execute(GameState state){
    }
    @Override
    public char[] getData(){
        return (getClass().getName()+":test").toCharArray();
    }
    @Override
    protected void   restore(char[] data){

    }
}
