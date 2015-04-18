package com.gameloop;

import com.renderer.Updatable;
import com.comms.*;
import java.util.List;
import java.lang.InterruptedException;
import java.util.ArrayList;

public class GameLoop extends Thread {

    private boolean running;

    //list that will be updated every thread loop
    private List<Updatable> updatables;

    private GameState gameState;

    public GameLoop(GameState state) {
        updatables = new ArrayList<Updatable>();
        running = true;
        //this is only temporary; the server should make its own in the future
        gameState = state;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void addUpdatable(Updatable up){
        synchronized(updatables){
            updatables.add(up);
        }
    }

    @Override
    public void run() {
        //this is acting as our server for now
        while (running) {
            synchronized(updatables){
                for (Updatable updatable : updatables)
                    updatable.update();
            }

            Command command = CommandHandler.getInstance().remove();
            //if there no command is not needed to be updated
            if (command != null) {
                command.execute(gameState);
            } else {
                try{
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    break; //break the loop if the thread is interrupted
                }
            }
        }
    }
}

