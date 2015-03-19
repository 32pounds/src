package com.gameloop;

import com.renderer.Updatable;

import java.util.List;

public class GameLoop extends Thread {

    private boolean running;

    //list that will be updated every thread loop
    private List<Updatable> updatables;

    public GameLoop(List<Updatable> list) {
        updatables = list;
    }

    public void setRunning(boolean running) {

        this.running = running;
    }

    @Override
    public void run() {
        if (running) {
            //updating updatable classes
            for (Updatable updatable : updatables)
                updatable.update();
        }
    }
}

