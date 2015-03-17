package com.gameloop;

import com.renderer.Updatable;

import java.util.List;

public class GameLoop extends Thread {

    private boolean running;

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
            for(Updatable updatable: updatables)
                updatable.update();
        }
    }
}

