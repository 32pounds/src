package com.gameloop;

import com.renderer.Updatable;
import com.comms.Command;
import com.comms.CommandHandler;
import java.util.List;
import java.lang.InterruptedException;

public class GameLoop extends Thread {

    private boolean running;

    //list that will be updated every thread loop
    private List<Updatable> updatables;

    public GameLoop(List<Updatable> list) {
        updatables = list;
        running = true;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        //this is acting as our server for now
        while (running) {
            Command command = CommandHandler.getInstance().remove();
            //if there no command is not needed to be updated
            if (command != null) {
                command.execute();
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

